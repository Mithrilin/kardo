package com.kardoaward.kardo.spectator_request.event_spectator_request.controller.admin;

import com.kardoaward.kardo.spectator_request.event_spectator_request.model.dto.EventSpectatorRequestDto;
import com.kardoaward.kardo.spectator_request.event_spectator_request.service.EventSpectatorRequestService;
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
@RequestMapping("/admin/spectators/event")
@Validated
public class EventSpectatorRequestAdminController {

    private final EventSpectatorRequestService service;

    @GetMapping("/{spectatorId}")
    public EventSpectatorRequestDto getEventSpectatorRequestByIdByAdmin(@PathVariable @Positive Long spectatorId) {
        log.info("Возвращение администратору заявки зрителя мероприятия с ИД {}.", spectatorId);
        return service.getEventSpectatorRequestByIdByAdmin(spectatorId);
    }
}
