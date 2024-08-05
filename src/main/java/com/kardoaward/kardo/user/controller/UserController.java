package com.kardoaward.kardo.user.controller;

import com.kardoaward.kardo.config.MyUserDetails;
import com.kardoaward.kardo.exception.BadRequestException;
import com.kardoaward.kardo.user.mapper.UserMapper;
import com.kardoaward.kardo.user.model.User;
import com.kardoaward.kardo.user.model.dto.NewUserRequest;
import com.kardoaward.kardo.user.model.dto.UpdateUserRequest;
import com.kardoaward.kardo.user.model.dto.UserDto;
import com.kardoaward.kardo.user.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/users")
@Validated
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping("/reg")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createUser(@RequestBody @Valid NewUserRequest newUserRequest) {
        log.info("Добавление нового пользователь {}.", newUserRequest);
        User user = userMapper.newUserRequestToUser(newUserRequest);
        User returnedUser = userService.addUser(user);
        return userMapper.userToUserDto(returnedUser);
    }

    @GetMapping("/{userId}")
    public UserDto getUserById(@PathVariable @Positive Long userId) {
        log.info("Получение пользователем с ИД {} своих данных.", userId);

        final MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long checkId = userDetails.getUser().getId();

        if (!userId.equals(checkId)) {
            log.error("Пользователь с ИД {} не может просматривать профиль пользователя с ИД {}.", checkId, userId);
            throw new BadRequestException(String.format("Пользователь с ИД %d не может просматривать " +
                    "профиль пользователя с ИД %d.", checkId, userId));
        }

        User returnedUser = userService.getUserById(userId);
        return userMapper.userToUserDto(returnedUser);
    }

    @DeleteMapping
    public void deleteUser() {
        final MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        log.info("Удаление пользователем с ИД {} своего профиля.", userDetails.getUser().getId());
        userService.deleteUser(userDetails.getUser().getId());
    }

    @PatchMapping
    public UserDto updateUser(@RequestBody @Valid UpdateUserRequest request) {
        final MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("Обновление пользователем с ИД {} своих данных.", userDetails.getUser().getId());
        User updatedUser = userService.updateUser(userDetails.getUser().getId(), request);
        return userMapper.userToUserDto(updatedUser);
    }
}
