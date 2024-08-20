package com.kardoaward.kardo.spectator_request.event_spectator_request.controller;

import com.kardoaward.kardo.security.UserDetailsImpl;
import com.kardoaward.kardo.spectator_request.event_spectator_request.dto.EventSpectatorRequestDto;
import com.kardoaward.kardo.spectator_request.event_spectator_request.dto.NewEventSpectatorRequest;
import com.kardoaward.kardo.spectator_request.event_spectator_request.service.EventSpectatorRequestService;
import com.kardoaward.kardo.spectator_request.event_spectator_request.service.helper.EventSpectatorRequestValidationHelper;
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
@RequestMapping("/spectators/event")
@Validated
@Tag(name = "Заявка зрителя на мероприятие: Users.", description = "API для работы с заявками зрителей на мероприятие " +
        "для зарегистрированных пользователей.")
public class EventSpectatorRequestController {

    private final EventSpectatorRequestService service;

    private final EventSpectatorRequestValidationHelper helper;

    @Operation(summary = "Добавление заявки зрителя на мероприятие.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Заявка добавлена.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = EventSpectatorRequestDto.class))}),
            @ApiResponse(responseCode = "400", description = "Запрос составлен некорректно", content = @Content),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован", content = @Content),
            @ApiResponse(responseCode = "404", description = "Мероприятие не найдено", content = @Content),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера", content = @Content)})
    @PostMapping
    @Secured("USER")
    public EventSpectatorRequestDto createEventSpectatorRequest(@Parameter(description = "Данные добавляемой заявки")
                                                                @RequestBody @Valid NewEventSpectatorRequest request) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        User requestor = userDetails.getUser();
        log.info("Добавление пользователем с ИД {} новой заявки зрителя мероприятия {}.", requestor.getId(), request);
        helper.isUserRequesterOrAdmin(requestor, request.getRequesterId());
        return service.addEventSpectatorRequest(requestor, request);
    }

    @Operation(summary = "Удаление заявки зрителя на мероприятие.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Заявка удалена.", content = @Content),
            @ApiResponse(responseCode = "400", description = "Запрос составлен некорректно", content = @Content),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован", content = @Content),
            @ApiResponse(responseCode = "404", description = "Заявка не найдена", content = @Content),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера", content = @Content)})
    @DeleteMapping("/{spectatorId}")
    @Secured("USER")
    public void deleteEventSpectatorRequestById(@Parameter(description = "id заявки")
                                                @PathVariable @Positive Long spectatorId) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        User requestor = userDetails.getUser();
        log.info("Удаление пользователем с ИД {} своей заявки зрителя мероприятия с ИД {}.", requestor.getId(),
                spectatorId);
        service.deleteEventSpectatorRequestById(requestor, spectatorId);
    }

    @Operation(summary = "Получение заявки зрителя на мероприятие по ИД.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Заявка найдена.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = EventSpectatorRequestDto.class))}),
            @ApiResponse(responseCode = "400", description = "Запрос составлен некорректно", content = @Content),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован", content = @Content),
            @ApiResponse(responseCode = "404", description = "Заявка не найдена", content = @Content),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера", content = @Content)})
    @GetMapping("/{spectatorId}")
    @Secured({"ADMIN", "USER"})
    public EventSpectatorRequestDto getEventSpectatorRequestById(@Parameter(description = "id заявки")
                                                                 @PathVariable @Positive Long spectatorId) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        User requestor = userDetails.getUser();
        log.debug("Возвращение заявки зрителя мероприятия с ИД {}.", spectatorId);
        return service.getEventSpectatorRequestById(requestor, spectatorId);
    }
}
