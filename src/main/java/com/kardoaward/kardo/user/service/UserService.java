package com.kardoaward.kardo.user.service;

import com.kardoaward.kardo.user.model.NewUserRequest;
import com.kardoaward.kardo.user.model.UserDto;

public interface UserService {

    UserDto addUser(NewUserRequest newUserRequest);
}
