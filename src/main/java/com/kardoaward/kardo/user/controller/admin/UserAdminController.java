package com.kardoaward.kardo.user.controller.admin;

import com.kardoaward.kardo.user.dto.UserShortDto;
import com.kardoaward.kardo.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/admin/users")
@Validated
@Tag(name = "Пользователь: Admin.", description = "API администратора для работы с пользователями.")
public class UserAdminController {

    private final UserService userService;

    @Operation(summary = "Получение списка пользователей.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Запрос составлен некорректно", content = @Content),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован", content = @Content),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден", content = @Content),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера", content = @Content)})
    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<UserShortDto> getUsersByIds(@Parameter(description = "Список ИД пользователей")
                                            @RequestParam(required = false) List<Long> ids,
                                            @Parameter(description = "Количество элементов, которые " +
                                                    "нужно пропустить для формирования текущего набора")
                                            @RequestParam(defaultValue = "0") @Min(0) int from,
                                            @Parameter(description = "Количество элементов в наборе")
                                            @RequestParam(defaultValue = "10") @Positive int size) {
        log.info("Возвращение администратору списка пользователей.");
        return userService.getUsersByIds(ids, from, size);
    }
}
