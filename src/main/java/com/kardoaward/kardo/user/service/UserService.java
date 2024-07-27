package com.kardoaward.kardo.user.service;

import com.kardoaward.kardo.user.model.User;
import com.kardoaward.kardo.user.model.dto.UpdateUserRequest;
import com.kardoaward.kardo.user.model.dto.UserShortDto;

import java.util.List;

public interface UserService {

    User addUser(User user);

    User getUserById(Long userId);

    void deleteUser(Long userId);

    List<User> getUsersByIds(List<Long> ids, int from, int size);

    User updateUser(Long userId, UpdateUserRequest request);

    List<UserShortDto> getContestantsBySelectionId(Long selectionId, int from, int size);
}
