package com.kardoaward.kardo.user.controller;

import com.kardoaward.kardo.exception.BadRequestException;
import com.kardoaward.kardo.user.mapper.UserMapper;
import com.kardoaward.kardo.user.model.dto.NewUserRequest;
import com.kardoaward.kardo.user.model.User;
import com.kardoaward.kardo.user.model.dto.UpdateUserRequest;
import com.kardoaward.kardo.user.model.dto.UserDto;
import com.kardoaward.kardo.user.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/users")
@Validated
public class UserController {

    private final UserService userService;

    private final UserMapper userMapper;

    @PostMapping
    public UserDto createUser(@RequestBody @Valid NewUserRequest newUserRequest) {
        log.info("Добавление нового пользователь {}.", newUserRequest);
        User user = userMapper.newUserRequestToUser(newUserRequest);
        User returnedUser = userService.addUser(user);
        return userMapper.userToUserDto(returnedUser);
    }

    @GetMapping("/{userId}")
    public UserDto getUserById(@RequestHeader("X-Requestor-Id") Long requestorId,
                               @PathVariable @Positive Long userId) {
        log.info("Получение пользователем с ИД {} своих данных.", userId);

        if (!userId.equals(requestorId)) {
            log.error("Пользователь с ИД {} не может просматривать профиль пользователя с ИД {}.", requestorId, userId);
            throw new BadRequestException(String.format("Пользователь с ИД %d не может просматривать " +
                    "профиль пользователя с ИД %d.", requestorId, userId));
        }

        User returnedUser = userService.getUserById(userId);
        return userMapper.userToUserDto(returnedUser);
    }

    @DeleteMapping
    public void deleteUser(@RequestHeader("X-Requestor-Id") Long requestorId) {
        log.info("Удаление пользователем с ИД {} своего профиля.", requestorId);
        userService.deleteUser(requestorId);
    }

    @PatchMapping
    public UserDto updateUser(@RequestHeader("X-Requestor-Id") Long requestorId,
                              @RequestBody @Valid UpdateUserRequest request) {
        log.info("Обновление пользователем с ИД {} своих данных.", requestorId);
        User updatedUser = userService.updateUser(requestorId, request);
        return userMapper.userToUserDto(updatedUser);
    }
}
