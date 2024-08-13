package com.kardoaward.kardo.grand_competition.controller.admin;

import com.kardoaward.kardo.grand_competition.model.dto.NewGrandCompetitionRequest;
import com.kardoaward.kardo.grand_competition.model.dto.GrandCompetitionDto;
import com.kardoaward.kardo.grand_competition.model.dto.UpdateGrandCompetitionRequest;
import com.kardoaward.kardo.grand_competition.service.GrandCompetitionService;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/admin/competitions")
@Validated
@Tag(name="Гранд-соревнование: Admin.", description="API администратора для работы с Гранд-соревнованиями.")
public class GrandCompetitionAdminController {

    private final GrandCompetitionService service;

    @Operation(summary = "Добавление администратором нового Гранд-соревнования.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Гранд-соревнование добавлено.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = GrandCompetitionDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Запрос составлен некорректно", content = @Content),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован", content = @Content),
            @ApiResponse(responseCode = "404", description = "Гранд-соревнование не найдено", content = @Content),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера", content = @Content)})
    @PostMapping
    @Secured("ADMIN")
    public GrandCompetitionDto createGrandCompetition(@Parameter(description = "Данные добавляемого Гранд-соревнования")
                                                      @RequestBody @Valid NewGrandCompetitionRequest newCompetition) {
        log.info("Добавление администратором нового гранд-соревнования {}.", newCompetition);
        return service.addGrandCompetition(newCompetition);
    }

    @Operation(summary = "Удаление администратором Гранд-соревнования по ИД.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Гранд-соревнование удалено.", content = @Content),
            @ApiResponse(responseCode = "400", description = "Запрос составлен некорректно", content = @Content),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован", content = @Content),
            @ApiResponse(responseCode = "404", description = "Гранд-соревнование не найдено", content = @Content),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера", content = @Content)})
    @DeleteMapping("/{competitionId}")
    @Secured("ADMIN")
    public void deleteGrandCompetition(@Parameter(description = "id Гранд-соревнования")
                                       @PathVariable @Positive Long competitionId) {
        log.info("Удаление администратором гранд-соревнования с ИД {}.", competitionId);
        service.deleteGrandCompetition(competitionId);
    }

    @Operation(summary = "Обновление администратором Гранд-соревнования по ИД.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Гранд-соревнование обновлено.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = GrandCompetitionDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Запрос составлен некорректно", content = @Content),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован", content = @Content),
            @ApiResponse(responseCode = "404", description = "Гранд-соревнование не найдено", content = @Content),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера", content = @Content)})
    @PatchMapping("/{competitionId}")
    @Secured("ADMIN")
    public GrandCompetitionDto updateGrandCompetition(@Parameter(description = "id Гранд-соревнования")
                                                      @PathVariable @Positive Long competitionId,
                                                      @Parameter(description = "Данные обновляемого Гранд-соревнования")
                                                      @RequestBody @Valid UpdateGrandCompetitionRequest request) {
        log.info("Обновление администратором гранд-соревнования с ИД {}.", competitionId);
        return service.updateGrandCompetition(competitionId, request);
    }
}
