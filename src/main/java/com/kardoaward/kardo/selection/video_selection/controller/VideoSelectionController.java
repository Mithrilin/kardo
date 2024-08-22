package com.kardoaward.kardo.selection.video_selection.controller;

import com.kardoaward.kardo.selection.video_selection.dto.VideoSelectionDto;
import com.kardoaward.kardo.selection.video_selection.service.VideoSelectionService;
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
@RequestMapping("/selections/video")
@Validated
@Tag(name = "Видео-отбор: Users.", description = "API для работы с видео-отборами для зарегистрированных пользователей.")
public class VideoSelectionController {

    private final VideoSelectionService videoSelectionService;

    @Operation(summary = "Получение видео-отбора.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Видео-отбор найден.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = VideoSelectionDto.class))}),
            @ApiResponse(responseCode = "400", description = "Запрос составлен некорректно", content = @Content),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован", content = @Content),
            @ApiResponse(responseCode = "404", description = "Видео-отбор не найден", content = @Content),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера", content = @Content)})
    @GetMapping("/{selectionId}")
    @Secured({"ADMIN", "USER"})
    public VideoSelectionDto getVideoSelectionById(@Parameter(description = "id видео-отбора")
                                                   @PathVariable @Positive Long selectionId) {
        log.info("Возвращение видео-отбора с ИД {}.", selectionId);
        return videoSelectionService.getVideoSelectionById(selectionId);
    }

    @Operation(summary = "Получение списка видео-отборов.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Запрос составлен некорректно", content = @Content),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован", content = @Content),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден", content = @Content),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера", content = @Content)})
    @GetMapping
    @Secured({"ADMIN", "USER"})
    public List<VideoSelectionDto> getVideoSelections(@Parameter(description = "Количество элементов, которые " +
            "нужно пропустить для формирования текущего набора")
                                                      @RequestParam(defaultValue = "0") @Min(0) int from,
                                                      @Parameter(description = "Количество элементов в наборе")
                                                      @RequestParam(defaultValue = "10") @Positive int size) {
        log.info("Возвращение списка видео-отборов.");
        return videoSelectionService.getVideoSelections(from, size);
    }

    @Operation(summary = "Получение списка видео-отборов к Гранд-соревнованию.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Запрос составлен некорректно", content = @Content),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован", content = @Content),
            @ApiResponse(responseCode = "404", description = "Гранд-соревнование не найдено", content = @Content),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера", content = @Content)})
    @GetMapping("/competitions/{competitionId}")
    @Secured({"ADMIN", "USER"})
    public List<VideoSelectionDto> getVideoSelectionsByGrandCompetitionId(
                                                           @Parameter(description = "id Гранд-соревнования")
                                                           @PathVariable @Positive Long competitionId,
                                                           @Parameter(description = "Количество элементов, которые " +
                                                           "нужно пропустить для формирования текущего набора")
                                                           @RequestParam(defaultValue = "0") @Min(0) int from,
                                                           @Parameter(description = "Количество элементов в наборе")
                                                           @RequestParam(defaultValue = "10") @Positive int size) {
        log.info("Возвращение списка видео-отборов к гранд-соревнованию с ИД {}.", competitionId);
        return videoSelectionService.getVideoSelectionsByGrandCompetitionId(competitionId, from, size);
    }
}
