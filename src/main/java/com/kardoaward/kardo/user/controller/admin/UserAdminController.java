package com.kardoaward.kardo.user.controller.admin;

import com.kardoaward.kardo.user.mapper.UserMapper;
import com.kardoaward.kardo.user.model.User;
import com.kardoaward.kardo.user.model.dto.UserDto;
import com.kardoaward.kardo.user.model.dto.UserShortDto;
import com.kardoaward.kardo.user.service.UserService;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/admin/users")
@Validated
public class UserAdminController {

    private final UserService userService;

    private final UserMapper userMapper;

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable @Positive Long userId) {
        log.info("Удаление администратором пользователя с ИД {}.", userId);
        userService.deleteUser(userId);
    }

    @GetMapping("/{userId}")
    public UserDto getUserById(@PathVariable @Positive Long userId) {
        log.info("Возвращение администратору информации о пользователе с ИД {}.", userId);
        User returnedUser = userService.getUserById(userId);
        return userMapper.userToUserDto(returnedUser);
    }

    @GetMapping
    public List<UserShortDto> getUsersByIds(@RequestParam(required = false) List<Long> ids,
                                            @RequestParam(defaultValue = "0") @Min(0) int from,
                                            @RequestParam(defaultValue = "10") @Positive int size) {
        log.info("Возвращение администратору списка пользователей.");
        List<User> users = userService.getUsersByIds(ids, from, size);
        return userMapper.userListToUserShortDtoList(users);
    }
}
