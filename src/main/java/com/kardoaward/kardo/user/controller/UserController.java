package com.kardoaward.kardo.user.controller;

import com.google.gson.Gson;
import com.kardoaward.kardo.user.model.dto.NewUserRequest;
import com.kardoaward.kardo.user.model.dto.UpdateUserRequest;
import com.kardoaward.kardo.user.model.dto.UserDto;
import com.kardoaward.kardo.user.model.dto.UserShortDto;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/users")
@Validated
public class UserController {

    private final UserService userService;

    private final UserValidationHelper userValidationHelper;

    @PostMapping
    public UserShortDto createUser(@RequestBody @Valid NewUserRequest newUserRequest) {
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
                              @RequestParam(value = "text", required = false) String json,
                              @RequestParam(value = "image", required = false) MultipartFile file) {
        log.info("Обновление пользователем с ИД {} своих данных.", requestorId);
        /* ToDo
            Разобраться как принимать составные запросы.
         */
        UpdateUserRequest request = new Gson().fromJson(json, UpdateUserRequest.class);
        return userService.updateUser(requestorId, request, file);
    }
}
