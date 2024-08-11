package com.kardoaward.kardo.selection.offline_selection.controller.admin;

import com.kardoaward.kardo.selection.offline_selection.model.dto.NewOfflineSelectionRequest;
import com.kardoaward.kardo.selection.offline_selection.model.dto.OfflineSelectionDto;
import com.kardoaward.kardo.selection.offline_selection.model.dto.UpdateOfflineSelectionRequest;
import com.kardoaward.kardo.selection.offline_selection.service.OfflineSelectionService;
import com.kardoaward.kardo.selection.offline_selection.service.helper.OfflineSelectionValidationHelper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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

    private final OfflineSelectionValidationHelper offlineSelectionValidationHelper;

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public OfflineSelectionDto createOfflineSelection(@RequestBody @Valid
                                                      NewOfflineSelectionRequest newOfflineSelectionRequest) {
        log.info("Добавление администратором нового оффлайн-отбора {}.", newOfflineSelectionRequest);
        offlineSelectionValidationHelper.isNewOfflineSelectionDateValid(newOfflineSelectionRequest);
        return offlineSelectionService.addOfflineSelection(newOfflineSelectionRequest);
    }

    @DeleteMapping("/{selectionId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteOfflineSelection(@PathVariable @Positive Long selectionId) {
        log.info("Удаление администратором оффлайн-отбора с ИД {}.", selectionId);
        offlineSelectionService.deleteOfflineSelection(selectionId);
    }

    @PatchMapping("/{selectionId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public OfflineSelectionDto updateOfflineSelectionById(@PathVariable @Positive Long selectionId,
                                                          @RequestBody @Valid UpdateOfflineSelectionRequest request) {
        log.info("Обновление администратором оффлайн-отбора с ИД {}.", selectionId);
        return offlineSelectionService.updateOfflineSelectionById(selectionId, request);
    }
}
