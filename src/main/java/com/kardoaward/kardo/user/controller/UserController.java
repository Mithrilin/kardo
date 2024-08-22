package com.kardoaward.kardo.user.controller;

import com.kardoaward.kardo.security.UserDetailsImpl;
import com.kardoaward.kardo.user.model.User;
import com.kardoaward.kardo.user.dto.NewUserRequest;
import com.kardoaward.kardo.user.dto.UpdateUserRequest;
import com.kardoaward.kardo.user.dto.UserDto;
import com.kardoaward.kardo.user.dto.UserShortDto;
import com.kardoaward.kardo.user.service.UserService;
import com.kardoaward.kardo.user.service.helper.UserValidationHelper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Пользователь: Users.", description = "API для работы с пользователями для зарегистрированных пользователей.")
public class UserController {

    private final UserService userService;

    private final UserValidationHelper userValidationHelper;

    @Operation(summary = "Добавление пользователя. Регистрация.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь добавлен.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserShortDto.class))}),
            @ApiResponse(responseCode = "400", description = "Запрос составлен некорректно", content = @Content),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован", content = @Content),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден", content = @Content),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера", content = @Content)})
    @PostMapping("/reg")
    public UserShortDto createUser(@Parameter(description = "Данные добавляемого пользователя")
                                   @RequestBody @Valid NewUserRequest newUserRequest) {
        log.info("Добавление нового пользователь {}.", newUserRequest);
        return userService.addUser(newUserRequest);
    }

    @Operation(summary = "Получение пользователя.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь найден.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDto.class))}),
            @ApiResponse(responseCode = "400", description = "Запрос составлен некорректно", content = @Content),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован", content = @Content),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден", content = @Content),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера", content = @Content)})
    @GetMapping("/{userId}")
    @Secured({"ADMIN", "USER"})
    public UserDto getUserById(@Parameter(description = "id пользователя")
                               @PathVariable @Positive Long userId) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        User requestor = userDetails.getUser();
        log.info("Получение данных пользователя с ИД {}.", userId);
        userValidationHelper.isUserOwnerOrAdmin(requestor, userId);
        return userService.getUserById(userId);
    }

    @Operation(summary = "Удаление пользователя.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь удален.", content = @Content),
            @ApiResponse(responseCode = "400", description = "Запрос составлен некорректно", content = @Content),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован", content = @Content),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден", content = @Content),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера", content = @Content)})
    @DeleteMapping("/{userId}")
    @Secured({"ADMIN", "USER"})
    public void deleteUser(@Parameter(description = "id пользователя")
                           @PathVariable @Positive Long userId) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        User requestor = userDetails.getUser();
        log.info("Удаление профиля пользователя с ИД {}.", userId);
        userValidationHelper.isUserOwnerOrAdmin(requestor, userId);
        userService.deleteUser(userId);
    }

    @Operation(summary = "Обновление пользователя.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь обновлён.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDto.class))}),
            @ApiResponse(responseCode = "400", description = "Запрос составлен некорректно", content = @Content),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован", content = @Content),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден", content = @Content),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера", content = @Content)})
    @PatchMapping
    @Secured({"ADMIN", "USER"})
    public UserDto updateUser(@Parameter(description = "Данные обновляемого пользователя")
                              @RequestBody @Valid UpdateUserRequest request) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        User requestor = userDetails.getUser();
        log.info("Обновление пользователем с ИД {} своих данных.", requestor.getId());
        return userService.updateUser(requestor, request);
    }

    @Operation(summary = "Обновление/добавление аватарки пользователя.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Аватарка обновлена/добавлена.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDto.class))}),
            @ApiResponse(responseCode = "400", description = "Запрос составлен некорректно", content = @Content),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован", content = @Content),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден", content = @Content),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера", content = @Content)})
    @PatchMapping("/avatar")
    @Secured({"ADMIN", "USER"})
    public UserDto addUserAvatar(@Parameter(description = "MultipartFile файл с аватаркой пользователя")
                                     @RequestParam("image") MultipartFile file) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        User requestor = userDetails.getUser();
        log.info("Обновление пользователем с ИД {} своей аватарки.", requestor.getId());
        return userService.addUserAvatar(requestor, file);
    }
}
