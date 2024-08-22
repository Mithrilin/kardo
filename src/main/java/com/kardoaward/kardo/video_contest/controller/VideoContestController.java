package com.kardoaward.kardo.video_contest.controller;

import com.kardoaward.kardo.video_contest.dto.VideoContestDto;
import com.kardoaward.kardo.video_contest.service.VideoContestService;
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
@RequestMapping("/contests")
@Validated
@Tag(name="Видео-соревнование: Users.", description="API для работы с видео-соревнованиями " +
        "для зарегистрированных пользователей.")
public class VideoContestController {

    private final VideoContestService service;

    @Operation(summary = "Получение видео-соревнования.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Видео-соревнование найдено.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = VideoContestDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Запрос составлен некорректно", content = @Content),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован", content = @Content),
            @ApiResponse(responseCode = "404", description = "Видео-соревнование не найдено", content = @Content),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера", content = @Content)})
    @GetMapping("/{contestId}")
    @Secured({"ADMIN", "USER"})
    public VideoContestDto getVideoContestById(@Parameter(description = "id видео-соревнования")
                                                   @PathVariable @Positive Long contestId) {
        log.info("Возвращение видео-конкурса с ИД {}.", contestId);
        return service.getVideoContestById(contestId);
    }

    @Operation(summary = "Получение списка видео-соревнований.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Запрос составлен некорректно", content = @Content),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован", content = @Content),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден", content = @Content),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера", content = @Content)})
    @GetMapping
    @Secured({"ADMIN", "USER"})
    public List<VideoContestDto> getVideoContests(@Parameter(description = "Количество элементов, которые " +
                                                          "нужно пропустить для формирования текущего набора")
                                                      @RequestParam(defaultValue = "0") @Min(0) int from,
                                                  @Parameter(description = "Количество элементов в наборе")
                                                      @RequestParam(defaultValue = "10") @Positive int size) {
        log.info("Возвращение списка видео-конкурсов.");
        return service.getVideoContests(from, size);
    }
}
