package com.kardoaward.kardo.event.controller;

import com.kardoaward.kardo.enums.Field;
import com.kardoaward.kardo.event.dto.EventDto;
import com.kardoaward.kardo.event.dto.EventShortDto;
import com.kardoaward.kardo.event.enums.EventProgram;
import com.kardoaward.kardo.event.model.params.EventRequestParams;
import com.kardoaward.kardo.event.service.EventService;
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

import java.time.LocalDate;
import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/events")
@Validated
@Tag(name="Мероприятия: Users.", description="API для работы с мероприятиями для зарегистрированных пользователей.")
public class EventController {

    private final EventService eventService;

    @Operation(summary = "Получение мероприятия к Гранд-соревнованию по ИД.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Мероприятие найдено.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EventDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Запрос составлен некорректно", content = @Content),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован", content = @Content),
            @ApiResponse(responseCode = "404", description = "Мероприятие не найдено", content = @Content),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера", content = @Content)})
    @GetMapping("/{eventId}")
    @Secured({"ADMIN", "USER"})
    public EventDto getEventById(@Parameter(description = "id мероприятия")
                                 @PathVariable @Positive Long eventId) {
        log.info("Возвращение мероприятия с ИД {}.", eventId);
        return eventService.getEventById(eventId);
    }

    @Operation(summary = "Получение списка мероприятия по заданным параметрам.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Запрос составлен некорректно", content = @Content),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован", content = @Content),
            @ApiResponse(responseCode = "404", description = "Мероприятие не найдено", content = @Content),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера", content = @Content)})
    @GetMapping
    @Secured({"ADMIN", "USER"})
    public List<EventShortDto> getEventsByParams(@Parameter(description = "id Гранд-соревнования")
                                                 @RequestParam(required = false) Long grandCompetitionId,
                                                 @Parameter(description = "Дата прохождения мероприятия")
                                                 @RequestParam(required = false) LocalDate day,
                                                 @Parameter(description = "Программа мероприятия")
                                                 @RequestParam(required = false) EventProgram program,
                                                 @Parameter(description = "Направление мероприятия")
                                                 @RequestParam(required = false) Field field,
                                                 @Parameter(description = "Количество элементов, которые нужно " +
                                                         "пропустить для формирования текущего набора")
                                                 @RequestParam(defaultValue = "0") @Min(0) int from,
                                                 @Parameter(description = "Количество элементов в наборе")
                                                 @RequestParam(defaultValue = "10") @Positive int size) {
        EventRequestParams eventRequestParams = new EventRequestParams(
                grandCompetitionId, day, program, field, from, size
        );
        log.info("Возвращение списка мероприятий по заданным параметрам.");
        return eventService.getEventsByParams(eventRequestParams);
    }
}
