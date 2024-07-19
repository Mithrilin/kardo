package com.kardoaward.kardo.user.service;

import com.kardoaward.kardo.user.mapper.UserMapper;
import com.kardoaward.kardo.user.model.NewUserRequest;
import com.kardoaward.kardo.user.model.User;
import com.kardoaward.kardo.user.model.UserDto;
import com.kardoaward.kardo.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Override
    @Transactional
    public UserDto addUser(NewUserRequest newUserRequest) {
        User user = userMapper.newUserRequestToUser(newUserRequest);
        User returnedUser = userRepository.save(user);
        UserDto returnedUserDto = userMapper.userToUserDto(returnedUser);
        log.info("Добавлен новый пользователь с ID = {}", returnedUserDto.getId());
        return returnedUserDto;
    }
}
