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
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
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

    private final String FOLDER_PATH = "C:/Users/Roman/Desktop/test/";

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
        File oldAvatarPath = new File(FOLDER_PATH + userId);

        try {
            FileUtils.deleteDirectory(oldAvatarPath);
        } catch (IOException e) {
            throw new FileContentException("Не удалось удалить директорию: " + oldAvatarPath.getPath());
        }

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

    @Override
    @Transactional
    public void uploadAvatar(Long requestorId, MultipartFile file) {
        User user = userValidationHelper.isUserPresent(requestorId);
        File oldAvatarPath = new File(FOLDER_PATH + requestorId + "/avatar/");
        oldAvatarPath.mkdirs();

        try {
            FileUtils.cleanDirectory(oldAvatarPath);
        } catch (IOException e) {
            throw new FileContentException("Не удалось очистить директорию: " + oldAvatarPath.getPath());
        }

        String avatarPath = FOLDER_PATH + requestorId + "/avatar/" + file.getOriginalFilename();

        try {
            file.transferTo(new File(avatarPath));
        } catch (IOException e) {
            throw new FileContentException("Не удалось сохранить файл: " + avatarPath);
        }

        user.setAvatarPhoto(avatarPath);
        userRepository.save(user);
        log.info("Аватар пользователем с ИД {} успешно добавлен.", requestorId);
    }

    @Override
    @Transactional
    public void deleteAvatar(Long requestorId) {
        User user = userValidationHelper.isUserPresent(requestorId);
        File oldAvatarPath = new File(FOLDER_PATH + requestorId + "/avatar/");

        try {
            FileUtils.cleanDirectory(oldAvatarPath);
        } catch (IOException e) {
            throw new FileContentException("Не удалось очистить директорию: " + oldAvatarPath.getPath());
        }

        user.setAvatarPhoto(null);
        userRepository.save(user);
        log.info("Аватар пользователем с ИД {} успешно удалён.", requestorId);
    }

    @Override
    public byte[] downloadAvatarByUserId(Long userId) {
        userValidationHelper.isUserPresent(userId);
        File avatarPath = new File(FOLDER_PATH + userId + "/avatar/");
        File[] files = avatarPath.listFiles();

        if (files == null) {
            return null;
        }

        File file = files[0];

        try {
            return Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            throw new FileContentException("Не удалось обработать файл.");
        }
    }
}
