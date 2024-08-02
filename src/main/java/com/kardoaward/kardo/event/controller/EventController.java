package com.kardoaward.kardo.event.controller;

import com.kardoaward.kardo.event.model.dto.EventDto;
import com.kardoaward.kardo.event.service.EventService;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/events")
@Validated
public class EventController {

    private final EventService eventService;

    @GetMapping("/{selectionId}")
    public EventDto getEventById(@PathVariable @Positive Long eventId) {
        log.info("Возвращение мероприятия с ИД {}.", eventId);
        return eventService.getEventById(eventId);
    }
}
