package com.kardoaward.kardo.spectator_request.selection_spectator_request.controller.admin;

import com.kardoaward.kardo.spectator_request.dto.update.SpectatorRequestStatusUpdateRequest;
import com.kardoaward.kardo.spectator_request.dto.update.SpectatorRequestStatusUpdateResult;
import com.kardoaward.kardo.spectator_request.selection_spectator_request.dto.SelectionSpectatorRequestDto;
import com.kardoaward.kardo.spectator_request.selection_spectator_request.service.SelectionSpectatorRequestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
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
@RequestMapping("/admin/spectators/selection")
@Validated
@Tag(name = "Заявка зрителя на отбор: Admin.", description = "API администратора для работы с заявками зрителей на отбор.")
public class SelectionSpectatorRequestAdminController {

    private final SelectionSpectatorRequestService service;

    @Operation(summary = "Получение списка заявок зрителей по ИД отбора.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Получен список постов пользователя", content = {
                    @Content(mediaType = "application/json", array =
                    @ArraySchema(schema = @Schema(implementation = SelectionSpectatorRequestDto.class)))}),
            @ApiResponse(responseCode = "400", description = "Запрос составлен некорректно", content = @Content),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован", content = @Content),
            @ApiResponse(responseCode = "404", description = "Мероприятие не найдено", content = @Content),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера", content = @Content)})
    @GetMapping("/selections/{selectionId}")
    @Secured("ADMIN")
    public List<SelectionSpectatorRequestDto> getSelectionSpectatorRequestBySelectionId(
                                                           @Parameter(description = "id отбора")
                                                           @PathVariable @Positive Long selectionId,
                                                           @Parameter(description = "Количество элементов, которые " +
                                                           "нужно пропустить для формирования текущего набора")
                                                           @RequestParam(defaultValue = "0") @Min(0) int from,
                                                           @Parameter(description = "Количество элементов в наборе")
                                                           @RequestParam(defaultValue = "10") @Positive int size) {
        log.info("Возвращение администратору списка заявок зрителей к отбору с ИД {}.", selectionId);
        return service.getSelectionSpectatorRequestBySelectionId(selectionId, from, size);
    }

    @Operation(summary = "Обновление администратором статуса заявок зрителей к отбору.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Заявки обновлены.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SpectatorRequestStatusUpdateResult.class))}),
            @ApiResponse(responseCode = "400", description = "Запрос составлен некорректно", content = @Content),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован", content = @Content),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден", content = @Content),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера", content = @Content)})

    @PatchMapping("/selections/{selectionId}")
    @Secured("ADMIN")
    public SpectatorRequestStatusUpdateResult updateSelectionSpectatorRequestStatusBySelectionId(
                                                    @Parameter(description = "id отбора")
                                                    @PathVariable @Positive Long selectionId,
                                                    @Parameter(description = "Данные обновляемых заявок")
                                                    @RequestBody @Valid SpectatorRequestStatusUpdateRequest request) {
        log.info("Обновление администратором статуса заявок зрителей к отбору с ИД {}.", selectionId);
        return service.updateSelectionSpectatorRequestStatusBySelectionId(selectionId, request);
    }
}
