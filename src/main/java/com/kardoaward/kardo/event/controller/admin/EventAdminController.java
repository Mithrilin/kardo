package com.kardoaward.kardo.event.controller.admin;

import com.kardoaward.kardo.event.model.dto.EventDto;
import com.kardoaward.kardo.event.model.dto.NewEventRequest;
import com.kardoaward.kardo.event.model.dto.UpdateEventRequest;
import com.kardoaward.kardo.event.service.EventService;
import com.kardoaward.kardo.event.service.helper.EventValidationHelper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    public EventDto createEvent(@RequestBody @Valid NewEventRequest newEventRequest) {
        log.info("Добавление администратором нового мероприятия к гранд-соревнованию с ИД {}.",
                newEventRequest.getCompetitionId());
        eventValidationHelper.isNewEventDateValid(newEventRequest);
        return eventService.addEvent(newEventRequest);
    }

    @DeleteMapping("/{eventId}")
    public void deleteEventById(@PathVariable @Positive Long eventId) {
        log.info("Удаление администратором мероприятия с ИД {}.", eventId);
        eventService.deleteEventById(eventId);
    }

    @PatchMapping("/{eventId}")
    public EventDto updateEventById(@PathVariable @Positive Long eventId,
                                    @RequestBody @Valid UpdateEventRequest request) {
        log.info("Обновление администратором мероприятия с ИД {}.", eventId);
        return eventService.updateEventById(eventId, request);
    }

    @PatchMapping("/{eventId}/logo")
    public void uploadLogo(@PathVariable @Positive Long eventId,
                           @RequestParam("image") MultipartFile file) {
        log.info("Добавление администратором логотипа к мероприятию с ИД {}.", eventId);
        eventService.uploadLogo(eventId, file);
    }

    @DeleteMapping("/{eventId}/logo")
    public void deleteLogo(@PathVariable @Positive Long eventId) {
        log.info("Удаление администратором логотипа мероприятия с ИД {}.", eventId);
        eventService.deleteLogo(eventId);
    }

    @GetMapping("/{eventId}/logo")
    public ResponseEntity<?> downloadLogoByEventId(@PathVariable @Positive Long eventId) {
        byte[] imageData = eventService.downloadLogoByEventId(eventId);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(imageData);
    }
}
