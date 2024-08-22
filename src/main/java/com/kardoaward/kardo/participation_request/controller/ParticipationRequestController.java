package com.kardoaward.kardo.participation_request.controller;

import com.kardoaward.kardo.security.UserDetailsImpl;
import com.kardoaward.kardo.participation_request.dto.NewParticipationRequest;
import com.kardoaward.kardo.participation_request.dto.ParticipationRequestDto;
import com.kardoaward.kardo.participation_request.dto.update.UpdateParticipationRequest;
import com.kardoaward.kardo.participation_request.service.ParticipationRequestService;
import com.kardoaward.kardo.participation_request.service.helper.ParticipationRequestValidationHelper;
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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/participations")
@Validated
@Tag(name = "Заявка на участие в отборе: Users.", description = "API для работы с заявками на участие в отборе " +
        "для зарегистрированных пользователей.")
public class ParticipationRequestController {

    private final ParticipationRequestService service;

    private final ParticipationRequestValidationHelper participationHelper;

    @Operation(summary = "Добавление заявки на участие в отборе.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Заявка добавлена.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ParticipationRequestDto.class))}),
            @ApiResponse(responseCode = "400", description = "Запрос составлен некорректно", content = @Content),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован", content = @Content),
            @ApiResponse(responseCode = "404", description = "Отбор не найден", content = @Content),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера", content = @Content)})
    @PostMapping
    @Secured("USER")
    public ParticipationRequestDto createParticipation(@Parameter(description = "Данные добавляемой заявки")
                                                       @RequestBody @Valid
                                                       NewParticipationRequest newParticipationRequest) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        User requestor = userDetails.getUser();
        log.info("Добавление пользователем с ИД {} новой заявки на участие в отборе с ИД {}.", requestor.getId(),
                newParticipationRequest.getSelectionId());
        participationHelper.isUserRequesterOrAdmin(requestor, newParticipationRequest.getRequesterId());
        return service.addParticipation(requestor, newParticipationRequest);
    }

    @Operation(summary = "Удаление заявки на участие в отборе по ИД.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Заявка удалена.", content = @Content),
            @ApiResponse(responseCode = "400", description = "Запрос составлен некорректно", content = @Content),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован", content = @Content),
            @ApiResponse(responseCode = "404", description = "Заявка не найдена", content = @Content),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера", content = @Content)})
    @DeleteMapping("/{participationId}")
    @Secured("USER")
    public void deleteParticipationById(@Parameter(description = "id заявки")
                                        @PathVariable @Positive Long participationId) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        User requestor = userDetails.getUser();
        log.info("Удаление пользователем с ИД {} своей заявки с ИД {} на участие в отборе.",
                requestor.getId(), participationId);
        service.deleteParticipationById(requestor, participationId);
    }

    @Operation(summary = "Получение заявки на участие в отборе.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Заявка найдена.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ParticipationRequestDto.class))}),
            @ApiResponse(responseCode = "400", description = "Запрос составлен некорректно", content = @Content),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован", content = @Content),
            @ApiResponse(responseCode = "404", description = "Заявка не найдена", content = @Content),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера", content = @Content)})
    @GetMapping("/{participationId}")
    @Secured({"ADMIN", "USER"})
    public ParticipationRequestDto getParticipationById(@Parameter(description = "id заявки")
                                                        @PathVariable @Positive Long participationId) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        User requestor = userDetails.getUser();
        log.info("Возвращение заявки с ИД {} на участие в отборе.", participationId);
        return service.getParticipationById(requestor, participationId);
    }

    @Operation(summary = "Обновление заявки на участие в отборе.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Заявка обновлена.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ParticipationRequestDto.class))}),
            @ApiResponse(responseCode = "400", description = "Запрос составлен некорректно", content = @Content),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован", content = @Content),
            @ApiResponse(responseCode = "404", description = "Заявка не найдена", content = @Content),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера", content = @Content)})
    @PatchMapping("/{participationId}")
    @Secured("USER")
    public ParticipationRequestDto updateParticipationById(@Parameter(description = "id заявки")
                                                           @PathVariable @Positive Long participationId,
                                                           @Parameter(description = "Данные для обновления заявки")
                                                           @RequestBody @Valid UpdateParticipationRequest request) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        Long requestorId = userDetails.getUser().getId();
        log.info("Обновление пользователем с ИД {} своей заявки с ИД {} на участие в отборе.", requestorId,
                participationId);
        return service.updateParticipationById(requestorId, participationId, request);
    }
}
