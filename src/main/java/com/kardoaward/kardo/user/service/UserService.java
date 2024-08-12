package com.kardoaward.kardo.user.service;

import com.kardoaward.kardo.user.model.User;
import com.kardoaward.kardo.user.model.dto.NewUserRequest;
import com.kardoaward.kardo.user.model.dto.UpdateUserRequest;
import com.kardoaward.kardo.user.model.dto.UserDto;
import com.kardoaward.kardo.user.model.dto.UserShortDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {

    UserShortDto addUser(NewUserRequest newUserRequest);

    UserDto getUserById(Long userId);

    void deleteUser(Long userId);

    List<UserShortDto> getUsersByIds(List<Long> ids, int from, int size);

    UserDto updateUser(User requestor, UpdateUserRequest request, MultipartFile file);

    List<UserShortDto> getContestantsByOfflineSelectionId(Long selectionId, int from, int size);
}
