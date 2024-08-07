package com.kardoaward.kardo.user.controller.admin;

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

    @DeleteMapping("/{userId}")
    public void deleteUserByAdmin(@PathVariable @Positive Long userId) {
        log.info("Удаление администратором пользователя с ИД {}.", userId);
        userService.deleteUser(userId);
    }

    @GetMapping("/{userId}")
    public UserDto getUserByIdByAdmin(@PathVariable @Positive Long userId) {
        log.info("Возвращение администратору информации о пользователе с ИД {}.", userId);
        return userService.getUserById(userId);
    }

    @GetMapping
    public List<UserShortDto> getUsersByIds(@RequestParam(required = false) List<Long> ids,
                                            @RequestParam(defaultValue = "0") @Min(0) int from,
                                            @RequestParam(defaultValue = "10") @Positive int size) {
        log.info("Возвращение администратору списка пользователей.");
        return userService.getUsersByIds(ids, from, size);
    }
}
