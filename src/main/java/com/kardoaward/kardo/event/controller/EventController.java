package com.kardoaward.kardo.event.controller;

import com.kardoaward.kardo.enums.Field;
import com.kardoaward.kardo.event.model.dto.EventDto;
import com.kardoaward.kardo.event.model.dto.EventShortDto;
import com.kardoaward.kardo.event.model.enums.EventProgram;
import com.kardoaward.kardo.event.model.params.EventRequestParams;
import com.kardoaward.kardo.event.service.EventService;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
public class EventController {

    private final EventService eventService;

    @GetMapping("/{eventId}")
    public EventDto getEventById(@PathVariable @Positive Long eventId) {
        log.info("Возвращение мероприятия с ИД {}.", eventId);
        return eventService.getEventById(eventId);
    }

    @GetMapping
    public List<EventShortDto> getEventsByParams(@RequestParam(required = false) Long grandCompetitionId,
                                                 @RequestParam(required = false) LocalDate day,
                                                 @RequestParam(required = false) EventProgram program,
                                                 @RequestParam(required = false) Field field,
                                                 @RequestParam(defaultValue = "0") @Min(0) int from,
                                                 @RequestParam(defaultValue = "10") @Positive int size) {
        EventRequestParams eventRequestParams = new EventRequestParams(
                grandCompetitionId, day, program, field, from, size
        );
        log.info("Возвращение списка мероприятий по заданным параметрам.");
        return eventService.getEventsByParams(eventRequestParams);
    }
}
