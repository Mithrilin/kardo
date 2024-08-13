package com.kardoaward.kardo.grand_competition.controller;

import com.kardoaward.kardo.grand_competition.model.dto.GrandCompetitionDto;
import com.kardoaward.kardo.grand_competition.service.GrandCompetitionService;
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
@RequestMapping("/competitions")
@Validated
@Tag(name="Гранд-соревнование: Users.", description="API для работы с Гранд-соревнованиями " +
        "для зарегистрированных пользователей.")
public class GrandCompetitionController {

    private final GrandCompetitionService service;

    @Operation(summary = "Получение Гранд-соревнования по ИД.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Гранд-соревнование найдено.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = GrandCompetitionDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Запрос составлен некорректно", content = @Content),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован", content = @Content),
            @ApiResponse(responseCode = "404", description = "Гранд-соревнование не найдено", content = @Content),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера", content = @Content)})
    @GetMapping("/{competitionId}")
    @Secured({"ADMIN", "USER"})
    public GrandCompetitionDto getGrandCompetitionById(@Parameter(description = "id Гранд-соревнования")
                                                       @PathVariable @Positive Long competitionId) {
        log.info("Возвращение гранд-соревнования с ИД {}.", competitionId);
        return service.getGrandCompetitionById(competitionId);
    }

    @Operation(summary = "Получение списка Гранд-соревнований.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Запрос составлен некорректно", content = @Content),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован", content = @Content),
            @ApiResponse(responseCode = "404", description = "Гранд-соревнование не найдено", content = @Content),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера", content = @Content)})
    @GetMapping
    @Secured({"ADMIN", "USER"})
    public List<GrandCompetitionDto> getGrandCompetitions(@Parameter(description = "Количество элементов, которые " +
                                                                "нужно пропустить для формирования текущего набора")
                                                          @RequestParam(defaultValue = "0") @Min(0) int from,
                                                          @Parameter(description = "Количество элементов в наборе")
                                                          @RequestParam(defaultValue = "10") @Positive int size) {
        log.info("Возвращение списка гранд-соревнований.");
        return service.getGrandCompetitions(from, size);
    }
}
