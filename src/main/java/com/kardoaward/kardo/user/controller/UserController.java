package com.kardoaward.kardo.user.controller;

import com.kardoaward.kardo.exception.BadRequestException;
import com.kardoaward.kardo.user.mapper.UserMapper;
import com.kardoaward.kardo.user.model.User;
import com.kardoaward.kardo.user.model.dto.NewUserRequest;
import com.kardoaward.kardo.user.model.dto.UpdateUserRequest;
import com.kardoaward.kardo.user.model.dto.UserDto;
import com.kardoaward.kardo.user.repository.UserRepository;
import com.kardoaward.kardo.user.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/users")
@Validated
public class UserController {

    private final UserService userService;
    private UserRepository repository;
    private final UserMapper userMapper;

    /*TODO тестовый эндпроит для незарегистрированных юзеров. Должен вести на какую-то стартовую страницу
    *  обсудить с фронтами, куда будет выводить*/
    @GetMapping("/welcome")
    public String welcome() {
        return "Добро пожаловать на сайт Kardo";
    }

    /*TODO тестовый эндпроит для регистрации/зарегистрированных, требует ввода логина и пароля
    *  реализация будет корректироваться до двух: зарегистрироваться и вход для зарегистрированных */
    @GetMapping("/auth")
    public String authorized() {
        return "Это страница Kardo для авторизованных пользователей";
    }

    @GetMapping("/admin")
    public List<User> getUsers() {
        User first = new User();
        User second = new User();
        User third = new User();

        List<User> users = List.of(first, second, third);
        return users;
    }

    @GetMapping("/admin/{id}")
    public String getUsersEmail(@PathVariable long id) {
        return repository.findUserById(id).getEmail();
    }

    @GetMapping("/user/{id}")
    public User getUserById(@PathVariable long id) {
        return repository.findUserById(id);
    }


    @PostMapping("/reg")
    @ResponseStatus(HttpStatus.CREATED)
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
