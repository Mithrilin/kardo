package com.kardoaward.kardo.user.service;

import com.kardoaward.kardo.selection.offline_selection.service.helper.OfflineSelectionValidationHelper;
import com.kardoaward.kardo.user.mapper.UserMapper;
import com.kardoaward.kardo.user.model.User;
import com.kardoaward.kardo.user.model.dto.NewUserRequest;
import com.kardoaward.kardo.user.model.dto.UpdateUserRequest;
import com.kardoaward.kardo.user.model.dto.UserDto;
import com.kardoaward.kardo.user.model.dto.UserShortDto;
import com.kardoaward.kardo.user.repository.UserRepository;
import com.kardoaward.kardo.user.service.helper.UserValidationHelper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final UserValidationHelper userValidationHelper;
    private final OfflineSelectionValidationHelper offlineSelectionValidationHelper;

    @Override
    @Transactional
    public UserDto addUser(NewUserRequest newUserRequest) {
        User user = userMapper.newUserRequestToUser(newUserRequest);
        User returnedUser = userRepository.save(user);
        UserDto userDto = userMapper.userToUserDto(returnedUser);
        log.info("Пользователь с ID = {} создан.", userDto.getId());
        return userDto;
    }

    @Override
    public UserDto getUserById(Long userId) {
        User user = userValidationHelper.isUserPresent(userId);
        UserDto userDto = userMapper.userToUserDto(user);
        log.info("Пользователь с ИД {} возвращен.", userId);
        return userDto;
    }

    @Override
    @Transactional
    public void deleteUser(Long userId) {
        userValidationHelper.isUserPresent(userId);
        userRepository.deleteById(userId);
        log.info("Пользователь с ID {} удалён.", userId);
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
            log.info("Не нашлось пользователей по заданным параметрам.");
            return new ArrayList<>();
        }

        List<User> users = usersPage.getContent();
        List<UserShortDto> userShortDtos = userMapper.userListToUserShortDtoList(users);
        log.info("Список пользователей с номера {} размером {} возвращён.", from, users.size());
        return userShortDtos;
    }

    @Override
    @Transactional
    public UserDto updateUser(Long userId, UpdateUserRequest request) {
        User user = userValidationHelper.isUserPresent(userId);
        userMapper.updateUser(request, user);
        User updatedUser = userRepository.save(user);
        UserDto userDto = userMapper.userToUserDto(updatedUser);
        log.info("Пользователь с ID {} обновлён.", userId);
        return userDto;
    }

    @Override
    public List<UserShortDto> getContestantsByOfflineSelectionId(Long selectionId, int from, int size) {
        offlineSelectionValidationHelper.isOfflineSelectionPresent(selectionId);
        int page = from / size;
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        Page<User> usersPage = userRepository.findAllBySelectionId(selectionId, pageRequest);

        if (usersPage.isEmpty()) {
            log.info("Не нашлось пользователей по заданным параметрам.");
            return new ArrayList<>();
        }

        List<User> users = usersPage.getContent();
        List<UserShortDto> userShortDtos = userMapper.userListToUserShortDtoList(users);
        log.info("Список участников оффлайн-отбора с ИД {} с номера {} размером {} возвращён.",
                selectionId, from, users.size());
        return userShortDtos;
    }
}
