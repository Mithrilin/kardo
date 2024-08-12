package com.kardoaward.kardo.event.controller.admin;

import com.google.gson.Gson;
import com.kardoaward.kardo.event.model.dto.EventDto;
import com.kardoaward.kardo.event.model.dto.NewEventRequest;
import com.kardoaward.kardo.event.model.dto.UpdateEventRequest;
import com.kardoaward.kardo.event.service.EventService;
import com.kardoaward.kardo.event.service.helper.EventValidationHelper;
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
public class EventAdminController {

    private final EventService eventService;

    private final EventValidationHelper eventValidationHelper;

    @PostMapping
    @Secured("ADMIN")
    public EventDto createEvent(@RequestParam("text") String json,
                                @RequestParam("video") MultipartFile file) {
        /* ToDo
            Разобраться как принимать составные запросы.
         */
        NewEventRequest newEventRequest = new Gson().fromJson(json, NewEventRequest.class);
        log.info("Добавление администратором нового мероприятия к гранд-соревнованию с ИД {}.",
                newEventRequest.getCompetitionId());
        eventValidationHelper.isNewEventDateValid(newEventRequest);
        return eventService.addEvent(newEventRequest, file);
    }

    @DeleteMapping("/{eventId}")
    @Secured("ADMIN")
    public void deleteEventById(@PathVariable @Positive Long eventId) {
        log.info("Удаление администратором мероприятия с ИД {}.", eventId);
        eventService.deleteEventById(eventId);
    }

    @PatchMapping("/{eventId}")
    @Secured("ADMIN")
    public EventDto updateEventById(@PathVariable @Positive Long eventId,
                                    @RequestBody @Valid UpdateEventRequest request) {
        log.info("Обновление администратором мероприятия с ИД {}.", eventId);
        return eventService.updateEventById(eventId, request);
    }
}
