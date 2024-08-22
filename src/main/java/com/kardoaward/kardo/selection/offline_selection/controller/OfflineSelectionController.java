package com.kardoaward.kardo.selection.offline_selection.controller;

import com.kardoaward.kardo.security.UserDetailsImpl;
import com.kardoaward.kardo.selection.offline_selection.dto.OfflineSelectionDto;
import com.kardoaward.kardo.selection.offline_selection.service.OfflineSelectionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/selections/offline")
@Validated
@Tag(name = "Оффлайн-отбор: Users.", description = "API для работы с оффлайн-отборами " +
        "для зарегистрированных пользователей.")
public class OfflineSelectionController {

    private final OfflineSelectionService offlineSelectionService;

    @Operation(summary = "Получение оффлайн-отбора.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Оффлайн-отбор найден.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = OfflineSelectionDto.class))}),
            @ApiResponse(responseCode = "400", description = "Запрос составлен некорректно", content = @Content),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован", content = @Content),
            @ApiResponse(responseCode = "404", description = "Оффлайн-отбор не найден", content = @Content),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера", content = @Content)})
    @GetMapping("/{selectionId}")
    @Secured({"ADMIN", "USER"})
    public OfflineSelectionDto getOfflineSelectionById(@Parameter(description = "id оффлайн-отбора")
                                                       @PathVariable @Positive Long selectionId) {
        log.info("Возвращение оффлайн-отбора с ИД {}.", selectionId);
        return offlineSelectionService.getOfflineSelectionById(selectionId);
    }

    @Operation(summary = "Получение списка оффлайн-отборов.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Запрос составлен некорректно", content = @Content),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован", content = @Content),
            @ApiResponse(responseCode = "404", description = "Оффлайн-отбор не найден", content = @Content),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера", content = @Content)})
    @GetMapping
    @Secured({"ADMIN", "USER"})
    public List<OfflineSelectionDto> getOfflineSelections(@Parameter(description = "Количество элементов, которые " +
            "нужно пропустить для формирования текущего набора")
                                                          @RequestParam(defaultValue = "0") @Min(0) int from,
                                                          @Parameter(description = "Количество элементов в наборе")
                                                          @RequestParam(defaultValue = "10") @Positive int size) {
        log.info("Возвращение списка оффлайн-отборов.");
        return offlineSelectionService.getOfflineSelections(from, size);
    }

    @Operation(summary = "Получение списка оффлайн-отборов с участием текущего пользователя.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Запрос составлен некорректно", content = @Content),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован", content = @Content),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден", content = @Content),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера", content = @Content)})
    @GetMapping("/contestants")
    @Secured("USER")
    public List<OfflineSelectionDto> getOfflineSelectionsByRequestorId(
                                                            @Parameter(description = "Количество элементов, которые " +
                                                            "нужно пропустить для формирования текущего набора")
                                                            @RequestParam(defaultValue = "0") @Min(0) int from,
                                                            @Parameter(description = "Количество элементов в наборе")
                                                            @RequestParam(defaultValue = "10") @Positive int size) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        Long requestorId = userDetails.getUser().getId();
        log.info("Возвращение списка оффлайн-отборов с участием пользователя с ИД {}.", requestorId);
        return offlineSelectionService.getOfflineSelectionsByRequestorId(requestorId, from, size);
    }

    @Operation(summary = "Получение списка оффлайн-отборов к Гранд-соревнованию.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Запрос составлен некорректно", content = @Content),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован", content = @Content),
            @ApiResponse(responseCode = "404", description = "Гранд-соревнование не найдено", content = @Content),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера", content = @Content)})
    @GetMapping("/competitions/{competitionId}")
    @Secured({"ADMIN", "USER"})
    public List<OfflineSelectionDto> getOfflineSelectionsByGrandCompetitionId(
                                                           @Parameter(description = "id Гранд-соревнования")
                                                           @PathVariable @Positive Long competitionId,
                                                           @Parameter(description = "Количество элементов, которые " +
                                                           "нужно пропустить для формирования текущего набора")
                                                           @RequestParam(defaultValue = "0") @Min(0) int from,
                                                           @Parameter(description = "Количество элементов в наборе")
                                                           @RequestParam(defaultValue = "10") @Positive int size) {
        log.info("Возвращение списка оффлайн-отборов к гранд-соревнованию с ИД {}.", competitionId);
        return offlineSelectionService.getOfflineSelectionsByGrandCompetitionId(competitionId, from, size);
    }
}
