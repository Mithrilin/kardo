package com.kardoaward.kardo.user.service;

import com.kardoaward.kardo.user.model.User;

public interface UserService {

    User addUser(User user);

    User getUserById(Long userId);
}
