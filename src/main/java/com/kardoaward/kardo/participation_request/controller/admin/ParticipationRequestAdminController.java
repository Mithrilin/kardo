package com.kardoaward.kardo.participation_request.controller.admin;

import com.kardoaward.kardo.participation_request.model.dto.ParticipationRequestDto;
import com.kardoaward.kardo.participation_request.service.ParticipationRequestService;
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
@RequestMapping("/admin/participations")
@Validated
public class ParticipationRequestAdminController {

    private final ParticipationRequestService service;

    @GetMapping("/{participationId}")
    public ParticipationRequestDto getParticipationByIdByAdmin(@PathVariable @Positive Long participationId) {
        log.info("Возвращение администратору заявки с ИД {} на участие в отборе.", participationId);
        return service.getParticipationByIdByAdmin(participationId);
    }
}
