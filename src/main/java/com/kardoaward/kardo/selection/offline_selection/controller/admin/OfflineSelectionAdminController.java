package com.kardoaward.kardo.selection.offline_selection.controller.admin;

import com.kardoaward.kardo.selection.offline_selection.dto.NewOfflineSelectionRequest;
import com.kardoaward.kardo.selection.offline_selection.dto.OfflineSelectionDto;
import com.kardoaward.kardo.selection.offline_selection.dto.UpdateOfflineSelectionRequest;
import com.kardoaward.kardo.selection.offline_selection.service.OfflineSelectionService;
import com.kardoaward.kardo.selection.offline_selection.service.helper.OfflineSelectionValidationHelper;
import com.kardoaward.kardo.user.dto.UserShortDto;
import com.kardoaward.kardo.user.service.UserService;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/admin/selections/offline")
@Validated
@Tag(name="Оффлайн-отбор: Admin.", description="API администратора для работы с оффлайн-отборами.")
public class OfflineSelectionAdminController {

    private final OfflineSelectionService offlineSelectionService;
    private final UserService userService;

    private final OfflineSelectionValidationHelper offlineSelectionValidationHelper;

    @Operation(summary = "Добавление оффлайн-отбора.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Оффлайн-отбор добавлен.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = OfflineSelectionDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Запрос составлен некорректно", content = @Content),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован", content = @Content),
            @ApiResponse(responseCode = "404", description = "Гранд-соревнование не найдено", content = @Content),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера", content = @Content)})
    @PostMapping
    @Secured("ADMIN")
    public OfflineSelectionDto createOfflineSelection(@Parameter(description = "Данные добавляемого отбора")
                                                      @RequestBody @Valid
                                                      NewOfflineSelectionRequest newOfflineSelectionRequest) {
        log.info("Добавление администратором нового оффлайн-отбора {}.", newOfflineSelectionRequest);
        offlineSelectionValidationHelper.isNewOfflineSelectionDateValid(newOfflineSelectionRequest);
        return offlineSelectionService.addOfflineSelection(newOfflineSelectionRequest);
    }

    @Operation(summary = "Удаление оффлайн-отбора.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Оффлайн-отбор удалён.", content = @Content),
            @ApiResponse(responseCode = "400", description = "Запрос составлен некорректно", content = @Content),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован", content = @Content),
            @ApiResponse(responseCode = "404", description = "Оффлайн-отбор не найден", content = @Content),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера", content = @Content)})
    @DeleteMapping("/{selectionId}")
    @Secured("ADMIN")
    public void deleteOfflineSelection(@Parameter(description = "id оффлайн-отбора")
                                       @PathVariable @Positive Long selectionId) {
        log.info("Удаление администратором оффлайн-отбора с ИД {}.", selectionId);
        offlineSelectionService.deleteOfflineSelection(selectionId);
    }

    @Operation(summary = "Обновление оффлайн-отбора.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Оффлайн-отбор обновлен.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = OfflineSelectionDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Запрос составлен некорректно", content = @Content),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован", content = @Content),
            @ApiResponse(responseCode = "404", description = "Оффлайн-отбор не найден", content = @Content),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера", content = @Content)})
    @PatchMapping("/{selectionId}")
    @Secured("ADMIN")
    public OfflineSelectionDto updateOfflineSelectionById(@Parameter(description = "id оффлайн-отбора")
                                                          @PathVariable @Positive Long selectionId,
                                                          @Parameter(description = "Данные обновляемого отбора")
                                                          @RequestBody @Valid UpdateOfflineSelectionRequest request) {
        log.info("Обновление администратором оффлайн-отбора с ИД {}.", selectionId);
        return offlineSelectionService.updateOfflineSelectionById(selectionId, request);
    }

    @Operation(summary = "Получение списка участников оффлайн-отбора.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Запрос составлен некорректно", content = @Content),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован", content = @Content),
            @ApiResponse(responseCode = "404", description = "Оффлайн-отбор не найден", content = @Content),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера", content = @Content)})
    @GetMapping("/{selectionId}/contestants")
    @Secured("ADMIN")
    public List<UserShortDto> getContestantsByOfflineSelectionId(
                                                            @Parameter(description = "id оффлайн-отбора")
                                                            @PathVariable @Positive Long selectionId,
                                                            @Parameter(description = "Количество элементов, которые " +
                                                            "нужно пропустить для формирования текущего набора")
                                                            @RequestParam(defaultValue = "0") @Min(0) int from,
                                                            @Parameter(description = "Количество элементов в наборе")
                                                            @RequestParam(defaultValue = "10") @Positive int size) {
        log.debug("Возвращение списка участников оффлайн-отбора с ИД {}.", selectionId);
        return userService.getContestantsByOfflineSelectionId(selectionId, from, size);
    }
}
