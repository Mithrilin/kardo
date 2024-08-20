package com.kardoaward.kardo.user.service;

import com.kardoaward.kardo.media_file.service.MediaFileService;
import com.kardoaward.kardo.selection.offline_selection.service.helper.OfflineSelectionValidationHelper;
import com.kardoaward.kardo.user.mapper.UserMapper;
import com.kardoaward.kardo.user.model.User;
import com.kardoaward.kardo.user.dto.NewUserRequest;
import com.kardoaward.kardo.user.dto.UpdateUserRequest;
import com.kardoaward.kardo.user.dto.UserDto;
import com.kardoaward.kardo.user.dto.UserShortDto;
import com.kardoaward.kardo.user.repository.UserRepository;
import com.kardoaward.kardo.user.service.helper.UserValidationHelper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final MediaFileService mediaFileService;

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final UserValidationHelper userValidationHelper;
    private final OfflineSelectionValidationHelper offlineSelectionValidationHelper;

    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserShortDto addUser(NewUserRequest newUserRequest) {
        User user = userMapper.newUserRequestToUser(newUserRequest,
                passwordEncoder.encode(newUserRequest.getPassword()));
        User returnedUser = userRepository.save(user);
        UserShortDto userShortDto = userMapper.userToUserShortDto(returnedUser);
        log.info("Пользователь с ID = {} создан.", userShortDto.getId());
        return userShortDto;
    }

    @Override
    public UserDto getUserById(Long userId) {
        User user = userValidationHelper.isUserPresent(userId);
        UserDto userDto = userMapper.userToUserDto(user);
        log.debug("Пользователь с ИД {} возвращен.", userId);
        return userDto;
    }

    @Override
    @Transactional
    public void deleteUser(User user) {
        mediaFileService.deleteUserDirectory(user);
        userRepository.delete(user);
        log.info("Пользователь с ID {} удалён.", user.getId());
    }

    @Override
    public List<UserShortDto> getUsersByIds(List<Long> ids, int from, int size) {
        int page = from / size;
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        Page<User> usersPage;

        if (ids == null) {
            usersPage = userRepository.findAll(pageRequest);
        } else {
            usersPage = userRepository.findByIdIn(ids, pageRequest);
        }
        if (usersPage.isEmpty()) {
            log.debug("Не нашлось пользователей по заданным параметрам.");
            return new ArrayList<>();
        }

        List<User> users = usersPage.getContent();
        List<UserShortDto> userShortDtos = userMapper.userListToUserShortDtoList(users);
        log.debug("Список пользователей с номера {} размером {} возвращён.", from, users.size());
        return userShortDtos;
    }

    @Override
    @Transactional
    public UserDto updateUser(User user, UpdateUserRequest request) {
        userMapper.updateUser(request, user);
        User updatedUser = userRepository.save(user);
        UserDto userDto = userMapper.userToUserDto(updatedUser);
        log.info("Пользователь с ID {} обновлён.", user.getId());
        return userDto;
    }

    @Override
    @Transactional
    public UserDto addAvatarToUser(User user, MultipartFile file) {
        mediaFileService.addAvatarToUser(user, file);
        User updatedUser = userRepository.save(user);
        UserDto userDto = userMapper.userToUserDto(updatedUser);
        log.info("Аватар пользователя с ID {} добавлен/обновлён.", user.getId());
        return userDto;
    }

    @Override
    @Transactional
    public void deleteAvatarFromUser(User user) {
        mediaFileService.deleteAvatarFromUser(user);
        userRepository.save(user);
        log.info("Аватар пользователя с ID {} удалён.", user.getId());
    }

    @Override
    public List<UserShortDto> getContestantsByOfflineSelectionId(Long selectionId, int from, int size) {
        offlineSelectionValidationHelper.isOfflineSelectionPresent(selectionId);
        int page = from / size;
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        Page<User> usersPage = userRepository.findAllBySelectionId(selectionId, pageRequest);

        if (usersPage.isEmpty()) {
            log.debug("Не нашлось пользователей по заданным параметрам.");
            return new ArrayList<>();
        }

        List<User> users = usersPage.getContent();
        List<UserShortDto> userShortDtos = userMapper.userListToUserShortDtoList(users);
        log.debug("Список участников оффлайн-отбора с ИД {} с номера {} размером {} возвращён.",
                selectionId, from, users.size());
        return userShortDtos;
    }
}
