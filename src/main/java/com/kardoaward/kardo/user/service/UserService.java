package com.kardoaward.kardo.user.service;

import com.kardoaward.kardo.user.model.dto.NewUserRequest;
import com.kardoaward.kardo.user.model.dto.UpdateUserRequest;
import com.kardoaward.kardo.user.model.dto.UserDto;
import com.kardoaward.kardo.user.model.dto.UserShortDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {

    UserDto addUser(NewUserRequest newUserRequest);

    UserDto getUserById(Long userId);

    void deleteUser(Long userId);

    List<UserShortDto> getUsersByIds(List<Long> ids, int from, int size);

    UserDto updateUser(Long userId, UpdateUserRequest request);

    List<UserShortDto> getContestantsByOfflineSelectionId(Long selectionId, int from, int size);

    void uploadAvatar(Long requestorId, MultipartFile file);

    void deleteAvatar(Long requestorId);

    byte[] downloadAvatarByUserId(Long userId);
}
