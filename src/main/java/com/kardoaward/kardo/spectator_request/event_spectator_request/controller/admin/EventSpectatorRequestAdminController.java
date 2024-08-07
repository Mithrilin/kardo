package com.kardoaward.kardo.spectator_request.event_spectator_request.controller.admin;

import com.kardoaward.kardo.spectator_request.event_spectator_request.model.dto.EventSpectatorRequestDto;
import com.kardoaward.kardo.spectator_request.event_spectator_request.service.EventSpectatorRequestService;
import com.kardoaward.kardo.spectator_request.model.dto.update.SpectatorRequestStatusUpdateRequest;
import com.kardoaward.kardo.spectator_request.model.dto.update.SpectatorRequestStatusUpdateResult;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping("/admin/spectators/event")
@Validated
public class EventSpectatorRequestAdminController {

    private final EventSpectatorRequestService service;

    @GetMapping("/{spectatorId}")
    public EventSpectatorRequestDto getEventSpectatorRequestByIdByAdmin(@PathVariable @Positive Long spectatorId) {
        log.info("Возвращение администратору заявки зрителя мероприятия с ИД {}.", spectatorId);
        return service.getEventSpectatorRequestByIdByAdmin(spectatorId);
    }

    @GetMapping("/events/{eventId}")
    public List<EventSpectatorRequestDto> getEventSpectatorRequestByEventId(@PathVariable @Positive Long eventId,
                                                                            @RequestParam(defaultValue = "0")
                                                                            @Min(0) int from,
                                                                            @RequestParam(defaultValue = "10")
                                                                            @Positive int size) {
        log.info("Возвращение администратору списка заявок зрителей к мероприятию с ИД {}.", eventId);
        return service.getEventSpectatorRequestByEventId(eventId, from, size);
    }

    @PatchMapping("/events/{eventId}")
    public SpectatorRequestStatusUpdateResult updateEventSpectatorRequestStatusByEventId(
                                                    @PathVariable @Positive Long eventId,
                                                    @RequestBody @Valid SpectatorRequestStatusUpdateRequest request) {
        log.info("Обновление администратором статуса заявок зрителей к мероприятию с ИД {}.", eventId);
        return service.updateEventSpectatorRequestStatusByEventId(eventId, request);
    }
}
