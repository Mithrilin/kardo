package com.kardoaward.kardo.event.controller.admin;

import com.kardoaward.kardo.event.dto.EventDto;
import com.kardoaward.kardo.event.dto.NewEventRequest;
import com.kardoaward.kardo.event.dto.UpdateEventRequest;
import com.kardoaward.kardo.event.service.EventService;
import com.kardoaward.kardo.event.service.helper.EventValidationHelper;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/admin/events")
@Validated
@Tag(name = "Мероприятия: Admin.", description = "API администратора для работы с мероприятиями.")
public class EventAdminController {

    private final EventService eventService;

    private final EventValidationHelper eventValidationHelper;

    @Operation(summary = "Добавление администратором нового мероприятия к Гранд-соревнованию.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Мероприятие добавлено.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = EventDto.class))}),
            @ApiResponse(responseCode = "400", description = "Запрос составлен некорректно", content = @Content),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован", content = @Content),
            @ApiResponse(responseCode = "404", description = "Гранд-соревнование не найдено", content = @Content),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера", content = @Content)})
    @PostMapping
    @Secured("ADMIN")
    public EventDto createEvent(@Parameter(description = "Данные добавляемого мероприятия")
                                @RequestBody @Valid NewEventRequest newEventRequest) {
        log.info("Добавление администратором нового мероприятия к гранд-соревнованию с ИД {}.",
                newEventRequest.getCompetitionId());
        eventValidationHelper.isNewEventDateValid(newEventRequest);
        return eventService.addEvent(newEventRequest);
    }

    @Operation(summary = "Добавление администратором логотипа к мероприятию.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Логотип к мероприятию добавлен.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = EventDto.class))}),
            @ApiResponse(responseCode = "400", description = "Запрос составлен некорректно", content = @Content),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован", content = @Content),
            @ApiResponse(responseCode = "404", description = "Мероприятие не найдено", content = @Content),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера", content = @Content)})
    @PostMapping("/{eventId}/logo")
    @Secured("ADMIN")
    public EventDto addEventLogo(@Parameter(description = "id мероприятия")
                                 @PathVariable @Positive Long eventId,
                                 @Parameter(description = "MultipartFile с логотипом")
                                 @RequestParam("image") MultipartFile file) {
        log.info("Добавление администратором логотипа к мероприятию с ИД {}.", eventId);
        return eventService.addEventLogo(eventId, file);
    }

    @Operation(summary = "Удаление администратором мероприятия по ИД.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Мероприятие удалено.", content = @Content),
            @ApiResponse(responseCode = "400", description = "Запрос составлен некорректно", content = @Content),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован", content = @Content),
            @ApiResponse(responseCode = "404", description = "Мероприятие не найдено", content = @Content),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера", content = @Content)})
    @DeleteMapping("/{eventId}")
    @Secured("ADMIN")
    public void deleteEventById(@Parameter(description = "id мероприятия")
                                @PathVariable @Positive Long eventId) {
        log.info("Удаление администратором мероприятия с ИД {}.", eventId);
        eventService.deleteEventById(eventId);
    }

    @Operation(summary = "Обновление администратором мероприятия по ИД.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Мероприятие обновлено.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = EventDto.class))}),
            @ApiResponse(responseCode = "400", description = "Запрос составлен некорректно", content = @Content),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован", content = @Content),
            @ApiResponse(responseCode = "404", description = "Мероприятие не найдено", content = @Content),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера", content = @Content)})
    @PatchMapping("/{eventId}")
    @Secured("ADMIN")
    public EventDto updateEventById(@Parameter(description = "id мероприятия")
                                    @PathVariable @Positive Long eventId,
                                    @Parameter(description = "Данные обновляемого мероприятия")
                                    @RequestBody @Valid UpdateEventRequest request) {
        log.info("Обновление администратором мероприятия с ИД {}.", eventId);
        return eventService.updateEventById(eventId, request);
    }
}
