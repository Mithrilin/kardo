package com.kardoaward.kardo.user.controller;

import com.kardoaward.kardo.security.UserDetailsImpl;
import com.kardoaward.kardo.user.model.User;
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
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PostMapping("/reg")
    public UserShortDto createUser(@RequestBody @Valid NewUserRequest newUserRequest) {
        log.info("Добавление нового пользователь {}.", newUserRequest);
        return userService.addUser(newUserRequest);
    }

    @GetMapping("/{userId}")
    @Secured({"ADMIN", "USER"})
    public UserDto getUserById(@PathVariable @Positive Long userId) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        User requestor = userDetails.getUser();
        log.info("Получение данных пользователя с ИД {}.", userId);
        userValidationHelper.isUserOwnerOrAdmin(requestor, userId);
        return userService.getUserById(userId);
    }

    @DeleteMapping("/{userId}")
    @Secured({"ADMIN", "USER"})
    public void deleteUser(@PathVariable @Positive Long userId) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        User requestor = userDetails.getUser();
        log.info("Удаление профиля пользователя с ИД {}.", userId);
        userValidationHelper.isUserOwnerOrAdmin(requestor, userId);
        userService.deleteUser(userId);
    }

    @PatchMapping
    @Secured({"ADMIN", "USER"})
    public UserDto updateUser(@RequestBody @Valid UpdateUserRequest request) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        User requestor = userDetails.getUser();
        log.info("Обновление пользователем с ИД {} своих данных.", requestor.getId());
        return userService.updateUser(requestor, request);
    }

    @PatchMapping("/avatar")
    @Secured({"ADMIN", "USER"})
    public UserDto addUserAvatar(@RequestParam("image") MultipartFile file) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        User requestor = userDetails.getUser();
        log.info("Обновление пользователем с ИД {} своей аватарки.", requestor.getId());
        return userService.addUserAvatar(requestor, file);
    }
}
