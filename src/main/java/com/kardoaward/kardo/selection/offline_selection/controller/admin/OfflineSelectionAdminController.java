package com.kardoaward.kardo.selection.offline_selection.controller.admin;

import com.kardoaward.kardo.exception.BadRequestException;
import com.kardoaward.kardo.selection.offline_selection.model.dto.NewOfflineSelectionRequest;
import com.kardoaward.kardo.selection.offline_selection.model.dto.OfflineSelectionDto;
import com.kardoaward.kardo.selection.offline_selection.service.OfflineSelectionService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/admin/selections/offline")
@Validated
public class OfflineSelectionAdminController {

    private final OfflineSelectionService offlineSelectionService;

    @PostMapping
    public OfflineSelectionDto createOfflineSelection(@RequestBody @Valid
                                                      NewOfflineSelectionRequest newOfflineSelectionRequest) {

        if (newOfflineSelectionRequest.getSelectionEnd().isBefore(newOfflineSelectionRequest.getSelectionStart())) {
            log.error("Дата начала оффлайн-отбора не может быть после его конца.");
            throw new BadRequestException("Дата начала оффлайн-отбора не может быть после его конца.");
        }

        log.info("Добавление администратором нового оффлайн-отбора {}.", newOfflineSelectionRequest);
        return offlineSelectionService.addOfflineSelection(newOfflineSelectionRequest);
    }

    @DeleteMapping("/{selectionId}")
    public void deleteOfflineSelection(@PathVariable @Positive Long selectionId) {
        log.info("Удаление администратором оффлайн-отбора с ИД {}.", selectionId);
        offlineSelectionService.deleteOfflineSelection(selectionId);
    }
}
