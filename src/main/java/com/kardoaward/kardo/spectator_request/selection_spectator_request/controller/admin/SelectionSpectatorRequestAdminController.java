package com.kardoaward.kardo.spectator_request.selection_spectator_request.controller.admin;

import com.kardoaward.kardo.spectator_request.selection_spectator_request.model.dto.SelectionSpectatorRequestDto;
import com.kardoaward.kardo.spectator_request.selection_spectator_request.service.SelectionSpectatorRequestService;
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
@RequestMapping("/admin/spectators/selection")
@Validated
public class SelectionSpectatorRequestAdminController {

    private final SelectionSpectatorRequestService service;

    @GetMapping("/{spectatorId}")
    public SelectionSpectatorRequestDto getSelectionSpectatorRequestByIdByAdmin(@PathVariable @Positive
                                                                                Long spectatorId) {
        log.info("Возвращение администратору заявки зрителя отбора с ИД {}.", spectatorId);
        return service.getSelectionSpectatorRequestByIdByAdmin(spectatorId);
    }
}
