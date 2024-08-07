package com.kardoaward.kardo.user.controller;

import com.kardoaward.kardo.user.model.dto.NewUserRequest;
import com.kardoaward.kardo.user.model.dto.UpdateUserRequest;
import com.kardoaward.kardo.user.model.dto.UserDto;
import com.kardoaward.kardo.user.service.UserService;
import com.kardoaward.kardo.user.service.helper.UserValidationHelper;
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

    private final UserValidationHelper userValidationHelper;

    @PostMapping
    public UserDto createUser(@RequestBody @Valid NewUserRequest newUserRequest) {
        log.info("Добавление нового пользователь {}.", newUserRequest);
        return userService.addUser(newUserRequest);
    }

    @GetMapping("/{userId}")
    public UserDto getUserById(@RequestHeader("X-Requestor-Id") Long requestorId,
                               @PathVariable @Positive Long userId) {
        log.info("Получение пользователем с ИД {} своих данных.", userId);
        userValidationHelper.isUserOwner(requestorId, userId);
        return userService.getUserById(userId);
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
        return userService.updateUser(requestorId, request);
    }
}
