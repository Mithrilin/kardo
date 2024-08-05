package com.kardoaward.kardo.participation_request.controller.admin;

import com.kardoaward.kardo.participation_request.model.dto.ParticipationRequestDto;
import com.kardoaward.kardo.participation_request.model.dto.update.ParticipationRequestStatusUpdateRequest;
import com.kardoaward.kardo.participation_request.model.dto.update.ParticipationRequestStatusUpdateResult;
import com.kardoaward.kardo.participation_request.service.ParticipationRequestService;
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
@RequestMapping("/admin/participations")
@Validated
public class ParticipationRequestAdminController {

    private final ParticipationRequestService service;

    @GetMapping("/{participationId}")
    public ParticipationRequestDto getParticipationByIdByAdmin(@PathVariable @Positive Long participationId) {
        log.info("Возвращение администратору заявки с ИД {} на участие в отборе.", participationId);
        return service.getParticipationByIdByAdmin(participationId);
    }

    @GetMapping("/selections/offline/{selectionId}")
    public List<ParticipationRequestDto> getParticipationsBySelectionId(@PathVariable @Positive Long selectionId,
                                                                        @RequestParam(defaultValue = "0")
                                                                        @Min(0) int from,
                                                                        @RequestParam(defaultValue = "10")
                                                                        @Positive int size) {
        log.info("Возвращение администратору списка заявок на участие в оффлайн-отборе с ИД {}.", selectionId);
        return service.getParticipationsBySelectionId(selectionId, from, size);
    }

    @PatchMapping("/selections/offline/{selectionId}")
    public ParticipationRequestStatusUpdateResult updateParticipationRequestStatusById (
                                                @PathVariable @Positive Long selectionId,
                                                @RequestBody @Valid ParticipationRequestStatusUpdateRequest request) {
        log.info("Обновление администратором статуса заявок на участие в оффлайн-отборе с ИД {}.", selectionId);
        return service.updateParticipationRequestStatusById(selectionId, request);
    }
}
