package com.kardoaward.kardo.user.service;

import com.kardoaward.kardo.user.mapper.UserMapper;
import com.kardoaward.kardo.user.model.NewUserRequest;
import com.kardoaward.kardo.user.model.User;
import com.kardoaward.kardo.user.model.UserDto;
import com.kardoaward.kardo.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;


}
