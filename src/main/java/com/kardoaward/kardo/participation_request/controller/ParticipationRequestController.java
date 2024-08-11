package com.kardoaward.kardo.participation_request.controller;

import com.kardoaward.kardo.security.UserDetailsImpl;
import com.kardoaward.kardo.participation_request.model.dto.NewParticipationRequest;
import com.kardoaward.kardo.participation_request.model.dto.ParticipationRequestDto;
import com.kardoaward.kardo.participation_request.model.dto.update.UpdateParticipationRequest;
import com.kardoaward.kardo.participation_request.service.ParticipationRequestService;
import com.kardoaward.kardo.participation_request.service.helper.ParticipationRequestValidationHelper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/participations")
@Validated
public class ParticipationRequestController {

    private final ParticipationRequestService service;

    private final ParticipationRequestValidationHelper participationHelper;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ParticipationRequestDto createParticipation(@RequestBody @Valid
                                                       NewParticipationRequest newParticipationRequest) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        Long requestorId = userDetails.getUser().getId();
        log.info("Добавление пользователем с ИД {} новой заявки на участие в отборе с ИД {}.", requestorId,
                newParticipationRequest.getSelectionId());
        participationHelper.isUserRequester(requestorId, newParticipationRequest.getRequesterId());
        return service.addParticipation(requestorId, newParticipationRequest);
    }

    @DeleteMapping("/{participationId}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public void deleteParticipationById(@PathVariable @Positive Long participationId) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        Long requestorId = userDetails.getUser().getId();
        log.info("Удаление пользователем с ИД {} своей заявки с ИД {} на участие в отборе.",
                requestorId, participationId);
        service.deleteParticipationById(requestorId, participationId);
    }

    @GetMapping("/{participationId}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ParticipationRequestDto getParticipationById(@PathVariable @Positive Long participationId) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        Long requestorId = userDetails.getUser().getId();
        log.info("Возвращение пользователю с ИД {} его заявки с ИД {} на участие в отборе.",
                requestorId, participationId);
        return service.getParticipationById(requestorId, participationId);
    }

    @PatchMapping("/{participationId}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ParticipationRequestDto updateParticipationById(@PathVariable @Positive Long participationId,
                                                           @RequestBody @Valid UpdateParticipationRequest request) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        Long requestorId = userDetails.getUser().getId();
        log.info("Обновление пользователем с ИД {} своей заявки с ИД {} на участие в отборе.", requestorId,
                participationId);
        return service.updateParticipationById(requestorId, participationId, request);
    }
}
