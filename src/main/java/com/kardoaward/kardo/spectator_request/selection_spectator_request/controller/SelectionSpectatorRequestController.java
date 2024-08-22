package com.kardoaward.kardo.spectator_request.selection_spectator_request.controller;

import com.kardoaward.kardo.security.UserDetailsImpl;
import com.kardoaward.kardo.spectator_request.selection_spectator_request.dto.NewSelectionSpectatorRequest;
import com.kardoaward.kardo.spectator_request.selection_spectator_request.dto.SelectionSpectatorRequestDto;
import com.kardoaward.kardo.spectator_request.selection_spectator_request.service.SelectionSpectatorRequestService;
import com.kardoaward.kardo.spectator_request.selection_spectator_request.service.helper.SelectionSpectatorRequestValidationHelper;
import com.kardoaward.kardo.user.model.User;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/spectators/selection")
@Validated
@Tag(name="Заявка зрителя на отбор: Users.", description="API для работы с заявками зрителей на отбор " +
        "для зарегистрированных пользователей.")
public class SelectionSpectatorRequestController {

    private final SelectionSpectatorRequestService service;

    private final SelectionSpectatorRequestValidationHelper helper;

    @Operation(summary = "Добавление заявки зрителя на отбор.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Заявка добавлена.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SelectionSpectatorRequestDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Запрос составлен некорректно", content = @Content),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован", content = @Content),
            @ApiResponse(responseCode = "404", description = "Отбор не найден", content = @Content),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера", content = @Content)})
    @PostMapping
    @Secured("USER")
    public SelectionSpectatorRequestDto createSelectionSpectatorRequest(
                                                                 @Parameter(description = "Данные добавляемой заявки")
                                                                 @RequestBody @Valid
                                                                 NewSelectionSpectatorRequest request) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        User requestor = userDetails.getUser();
        log.info("Добавление пользователем с ИД {} новой заявки зрителя отбора {}.", requestor.getId(), request);
        helper.isUserRequesterOrAdmin(requestor, request.getRequesterId());
        return service.addSelectionSpectatorRequest(requestor, request);
    }

    @Operation(summary = "Удаление заявки зрителя на отбор.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Заявка удалена.", content = @Content),
            @ApiResponse(responseCode = "400", description = "Запрос составлен некорректно", content = @Content),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован", content = @Content),
            @ApiResponse(responseCode = "404", description = "Заявка не найдена", content = @Content),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера", content = @Content)})
    @DeleteMapping("/{spectatorId}")
    @Secured("USER")
    public void deleteSelectionSpectatorRequestById(@Parameter(description = "id заявки")
                                                    @PathVariable @Positive Long spectatorId) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        User requestor = userDetails.getUser();
        log.info("Удаление пользователем с ИД {} своей заявки зрителя отбора с ИД {}.", requestor.getId(), spectatorId);
        service.deleteSelectionSpectatorRequestById(requestor, spectatorId);
    }

    @Operation(summary = "Получение заявки зрителя на отбор.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Заявка найдена.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SelectionSpectatorRequestDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Запрос составлен некорректно", content = @Content),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован", content = @Content),
            @ApiResponse(responseCode = "404", description = "Заявка не найдена", content = @Content),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера", content = @Content)})
    @GetMapping("/{spectatorId}")
    @Secured({"ADMIN", "USER"})
    public SelectionSpectatorRequestDto getSelectionSpectatorRequestById(@Parameter(description = "id заявки")
                                                                         @PathVariable @Positive Long spectatorId) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        User requestor = userDetails.getUser();
        log.info("Возвращение заявки зрителя отбора с ИД {}.", spectatorId);
        return service.getSelectionSpectatorRequestById(requestor, spectatorId);
    }
}
