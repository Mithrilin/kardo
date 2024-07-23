package com.kardoaward.kardo.user.controller;

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
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
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
    //ToDo Какой статус возвращать фронту и нужно ли?
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createUser(@RequestBody @Valid NewUserRequest newUserRequest) {
        log.info("Добавление нового пользователь {}.", newUserRequest);
        User user = userMapper.newUserRequestToUser(newUserRequest);
        User returnedUser = userService.addUser(user);
        return userMapper.userToUserDto(returnedUser);
    }

    @GetMapping("/{userId}")
    /* ToDo
        Какой статус возвращать фронту и нужно ли?
        Как проверить, что юзер запросил данные именно своего профиля?
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public UserDto getUserById(@PathVariable @Positive Long userId) {
        log.info("Получение пользователем с ИД {} своих данных.", userId);
        User returnedUser = userService.getUserById(userId);
        return userMapper.userToUserDto(returnedUser);
    }

    @DeleteMapping("/{userId}")
    /* ToDo
        Какой статус возвращать фронту и нужно ли?
        Как проверить, что юзер удаляет именно свой профиль?
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable @Positive Long userId) {
        log.info("Удаление пользователем с ИД {} своего профиля.", userId);
        userService.deleteUser(userId);
    }

    @PatchMapping("/{userId}")
    /* ToDo
        Какой статус возвращать фронту и нужно ли?
        Как проверить, что юзер обновляет именно свой профиль?
     */
    public UserDto updateUser(@PathVariable @Positive Long userId,
                              @RequestBody @Valid UpdateUserRequest request) {
        log.info("Обновление пользователем с ИД {} своих данных.", userId);
        User updatedUser = userService.updateUser(userId, request);
        return userMapper.userToUserDto(updatedUser);
    }
}
