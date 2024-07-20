package com.kardoaward.kardo.user.service;

import com.kardoaward.kardo.user.model.User;

import java.util.List;

public interface UserService {

    User addUser(User user);

    User getUserById(Long userId);

    void deleteUser(Long userId);

    List<User> getUsersByIds(List<Long> ids, int from, int size);
}
