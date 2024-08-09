package com.kardoaward.kardo.user.service;

import com.kardoaward.kardo.exception.FileContentException;
import com.kardoaward.kardo.exception.NotFoundException;
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

    private final String FOLDER_PATH = "C:/Users/Roman/Desktop/test/users/";

    @Override
    @Transactional
    public UserShortDto addUser(NewUserRequest newUserRequest) {
        User user = userMapper.newUserRequestToUser(newUserRequest);
        User returnedUser = userRepository.save(user);
        UserShortDto userShortDto = userMapper.userToUserShortDto(returnedUser);
        log.info("Пользователь с ID = {} создан.", userShortDto.getId());
        return userShortDto;
    }

    @Override
    public UserDto getUserById(Long userId) {
        User user = userValidationHelper.isUserPresent(userId);
        UserDto userDto = userMapper.userToUserDto(user);

        if (user.getAvatarPhoto() != null) {
            File avatarPath = new File(user.getAvatarPhoto());

            try {
                userDto.setAvatarPhoto(Files.readAllBytes(avatarPath.toPath()));
            } catch (IOException e) {
                throw new FileContentException("Не удалось обработать файл.");
            }
        }

        log.info("Пользователь с ИД {} возвращен.", userId);
        return userDto;
    }

    @Override
    @Transactional
    public void deleteUser(Long userId) {
        userValidationHelper.isUserPresent(userId);
        File userPath = new File(FOLDER_PATH + userId);

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
    public UserShortDto updateUser(Long userId, UpdateUserRequest request) {
        User user = userValidationHelper.isUserPresent(userId);
        userMapper.updateUser(request, user);
        User updatedUser = userRepository.save(user);
        UserShortDto userShortDto = userMapper.userToUserShortDto(updatedUser);
        log.info("Пользователь с ID {} обновлён.", userId);
        return userShortDto;
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
    public UserShortDto uploadAvatar(Long requestorId, MultipartFile file) {
        User user = userValidationHelper.isUserPresent(requestorId);

        if (user.getAvatarPhoto() != null) {
            try {
                FileUtils.forceDelete(new File(user.getAvatarPhoto()));
            } catch (IOException e) {
                throw new FileContentException("Не удалось очистить директорию: " + user.getAvatarPhoto());
            }
        }

        String path = FOLDER_PATH + requestorId + "/avatar/";
        File avatarPath = new File(path);
        avatarPath.mkdirs();
        String newAvatarPath = path + file.getOriginalFilename();

        try {
            file.transferTo(new File(newAvatarPath));
        } catch (IOException e) {
            throw new FileContentException("Не удалось сохранить файл: " + newAvatarPath);
        }

        user.setAvatarPhoto(newAvatarPath);
        User updatedUser = userRepository.save(user);
        UserShortDto userShortDto = userMapper.userToUserShortDto(updatedUser);
        log.info("Аватар пользователем с ИД {} успешно добавлен.", requestorId);
        return userShortDto;
    }

    @Override
    @Transactional
    public void deleteAvatar(Long requestorId) {
        User user = userValidationHelper.isUserPresent(requestorId);

        if (user.getAvatarPhoto() == null) {
            throw new NotFoundException(String.format("У пользователя с ИД %d отсутствует аватар.", requestorId));
        }

        File avatarPath = new File(user.getAvatarPhoto());

        try {
            FileUtils.forceDelete(avatarPath);
        } catch (IOException e) {
            throw new FileContentException("Не удалось удалить файл: " + avatarPath.getPath());
        }

        user.setAvatarPhoto(null);
        userRepository.save(user);
        log.info("Аватар пользователем с ИД {} успешно удалён.", requestorId);
    }
}
