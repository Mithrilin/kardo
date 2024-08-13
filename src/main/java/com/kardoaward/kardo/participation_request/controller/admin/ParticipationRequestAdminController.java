package com.kardoaward.kardo.participation_request.controller.admin;

import com.kardoaward.kardo.event.model.dto.EventDto;
import com.kardoaward.kardo.participation_request.model.dto.ParticipationRequestDto;
import com.kardoaward.kardo.participation_request.model.dto.update.ParticipationRequestStatusUpdateRequest;
import com.kardoaward.kardo.participation_request.model.dto.update.ParticipationRequestStatusUpdateResult;
import com.kardoaward.kardo.participation_request.service.ParticipationRequestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/admin/participations")
@Validated
@Tag(name="Заявка на участие в отборе: Admin.", description="API администратора для работы с заявками " +
        "на участие в отборе.")
public class ParticipationRequestAdminController {

    private final ParticipationRequestService service;

    @Operation(summary = "Получение списка заявок на участие в конкретном отборе.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Запрос составлен некорректно", content = @Content),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован", content = @Content),
            @ApiResponse(responseCode = "404", description = "Гранд-соревнование не найдено", content = @Content),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера", content = @Content)})
    @GetMapping("/selections/offline/{selectionId}")
    @Secured("ADMIN")
    public List<ParticipationRequestDto> getParticipationsBySelectionId(
                                                           @Parameter(description = "id Гранд-соревнования")
                                                           @PathVariable @Positive Long selectionId,
                                                           @Parameter(description = "Количество элементов, которые " +
                                                           "нужно пропустить для формирования текущего набора")
                                                           @RequestParam(defaultValue = "0") @Min(0) int from,
                                                           @Parameter(description = "Количество элементов в наборе")
                                                           @RequestParam(defaultValue = "10") @Positive int size) {
        log.info("Возвращение администратору списка заявок на участие в оффлайн-отборе с ИД {}.", selectionId);
        return service.getParticipationsBySelectionId(selectionId, from, size);
    }

    @Operation(summary = "Обновление заявок на участие в конкретном отборе.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Заявки обновлены.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ParticipationRequestStatusUpdateResult.class)) }),
            @ApiResponse(responseCode = "400", description = "Запрос составлен некорректно", content = @Content),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован", content = @Content),
            @ApiResponse(responseCode = "404", description = "Отбор не найден", content = @Content),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера", content = @Content)})
    @PatchMapping("/selections/offline/{selectionId}")
    @Secured("ADMIN")
    public ParticipationRequestStatusUpdateResult updateParticipationRequestStatus (
                                                @Parameter(description = "id отбора")
                                                @PathVariable @Positive Long selectionId,
                                                @Parameter(description = "Данные для обновления заявок")
                                                @RequestBody @Valid ParticipationRequestStatusUpdateRequest request) {
        log.info("Обновление администратором статуса заявок на участие в оффлайн-отборе с ИД {}.", selectionId);
        return service.updateParticipationRequestStatus(selectionId, request);
    }
}
