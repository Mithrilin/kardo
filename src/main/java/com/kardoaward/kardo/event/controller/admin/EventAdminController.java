package com.kardoaward.kardo.event.controller.admin;

import com.kardoaward.kardo.event.model.dto.EventDto;
import com.kardoaward.kardo.event.model.dto.NewEventRequest;
import com.kardoaward.kardo.event.service.EventService;
import com.kardoaward.kardo.exception.BadRequestException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/admin/events")
@Validated
public class EventAdminController {

    private final EventService eventService;

    @PostMapping
    public EventDto createEvent(@RequestBody @Valid NewEventRequest newEventRequest) {

        if (newEventRequest.getEventEnd().isBefore(newEventRequest.getEventStart())) {
            log.error("Дата и время начала мероприятия не может быть после его конца.");
            throw new BadRequestException("Дата и время начала мероприятия не может быть после его конца.");
        }

        log.info("Добавление администратором нового мероприятия к гранд-соревнованию с ИД {}.",
                newEventRequest.getCompetitionId());
        return eventService.addEvent(newEventRequest);
    }
}
