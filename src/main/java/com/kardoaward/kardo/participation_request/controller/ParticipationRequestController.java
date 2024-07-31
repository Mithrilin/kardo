package com.kardoaward.kardo.participation_request.controller;

import com.kardoaward.kardo.participation_request.model.dto.NewParticipationRequest;
import com.kardoaward.kardo.participation_request.model.dto.ParticipationRequestDto;
import com.kardoaward.kardo.participation_request.model.dto.update.UpdateParticipationRequest;
import com.kardoaward.kardo.participation_request.service.ParticipationRequestService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/participations")
@Validated
public class ParticipationRequestController {

    private final ParticipationRequestService service;

    @PostMapping
    public ParticipationRequestDto createParticipation(@RequestHeader("X-Requestor-Id") Long requestorId,
                                                       @RequestBody @Valid NewParticipationRequest newParticipationRequest) {
        log.info("Добавление пользователем с ИД {} новой заявки на участие в отборе с ИД {}.", requestorId,
                newParticipationRequest.getSelectionId());
        return service.addParticipation(requestorId, newParticipationRequest);
    }

    @DeleteMapping("/{participationId}")
    public void deleteParticipationById(@RequestHeader("X-Requestor-Id") Long requestorId,
                                        @PathVariable @Positive Long participationId) {
        log.info("Удаление пользователем с ИД {} своей заявки с ИД {} на участие в отборе.", requestorId, participationId);
        service.deleteParticipationById(requestorId, participationId);
    }

    @GetMapping("/{participationId}")
    public ParticipationRequestDto getParticipationById(@RequestHeader("X-Requestor-Id") Long requestorId,
                                                        @PathVariable @Positive Long participationId) {
        log.info("Возвращение пользователю с ИД {} его заявки с ИД {} на участие в отборе.", requestorId, participationId);
        return service.getParticipationById(requestorId, participationId);
    }

    @PatchMapping("/{participationId}")
    public ParticipationRequestDto updateParticipationById(@RequestHeader("X-Requestor-Id") Long requestorId,
                                                           @PathVariable @Positive Long participationId,
                                                           @RequestBody @Valid UpdateParticipationRequest request) {
        log.info("Обновление пользователем с ИД {} своей заявки с ИД {} на участие в отборе.", requestorId,
                participationId);
        return service.updateParticipationById(requestorId, participationId, request);
    }
}
