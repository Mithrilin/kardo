package com.kardoaward.kardo.user.service;

import com.kardoaward.kardo.exception.FileContentException;
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
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final UserValidationHelper userValidationHelper;
    private final OfflineSelectionValidationHelper offlineSelectionValidationHelper;

    private final PasswordEncoder passwordEncoder;

    private final String FOLDER_PATH;

    public UserServiceImpl(UserRepository userRepository,
                           UserMapper userMapper,
                           UserValidationHelper userValidationHelper,
                           OfflineSelectionValidationHelper offlineSelectionValidationHelper,
                           PasswordEncoder passwordEncoder,
                           @Value("${folder.path}") String FOLDER_PATH) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.userValidationHelper = userValidationHelper;
        this.offlineSelectionValidationHelper = offlineSelectionValidationHelper;
        this.passwordEncoder = passwordEncoder;
        this.FOLDER_PATH = FOLDER_PATH;
    }

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
        log.info("Пользователь с ИД {} возвращен.", userId);
        return userDto;
    }

    @Override
    @Transactional
    public void deleteUser(Long userId) {
        File userPath = new File(FOLDER_PATH + "/users/" + userId);

        try {
            FileUtils.deleteDirectory(userPath);
        } catch (IOException e) {
            throw new FileContentException("Не удалось удалить директорию: " + userPath.getPath());
        }

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
    public UserDto updateUser(User user, UpdateUserRequest request, MultipartFile file) {

        if (user.getAvatarPhoto() != null) {
            try {
                FileUtils.forceDelete(new File(user.getAvatarPhoto()));
                user.setAvatarPhoto(null);
            } catch (IOException e) {
                throw new FileContentException("Не удалось очистить директорию: " + user.getAvatarPhoto());
            }
        }

        if (file != null) {
            String path = FOLDER_PATH + "/users/" + user.getId() + "/avatar/";
            File avatarPath = new File(path);
            avatarPath.mkdirs();
            String newAvatarPath = path + file.getOriginalFilename();

            try {
                file.transferTo(new File(newAvatarPath));
            } catch (IOException e) {
                throw new FileContentException("Не удалось сохранить файл: " + newAvatarPath);
            }

            user.setAvatarPhoto(newAvatarPath);
        }

        userMapper.updateUser(request, user);
        User updatedUser = userRepository.save(user);
        UserDto userDto = userMapper.userToUserDto(updatedUser);
        log.info("Пользователь с ID {} обновлён.", user.getId());
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
