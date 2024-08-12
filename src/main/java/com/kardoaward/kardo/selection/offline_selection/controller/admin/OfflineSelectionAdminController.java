package com.kardoaward.kardo.selection.offline_selection.controller.admin;

import com.kardoaward.kardo.selection.offline_selection.model.dto.NewOfflineSelectionRequest;
import com.kardoaward.kardo.selection.offline_selection.model.dto.OfflineSelectionDto;
import com.kardoaward.kardo.selection.offline_selection.model.dto.UpdateOfflineSelectionRequest;
import com.kardoaward.kardo.selection.offline_selection.service.OfflineSelectionService;
import com.kardoaward.kardo.selection.offline_selection.service.helper.OfflineSelectionValidationHelper;
import com.kardoaward.kardo.user.model.dto.UserShortDto;
import com.kardoaward.kardo.user.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
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

import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/admin/selections/offline")
@Validated
public class OfflineSelectionAdminController {

    private final OfflineSelectionService offlineSelectionService;
    private final UserService userService;

    private final OfflineSelectionValidationHelper offlineSelectionValidationHelper;

    @PostMapping
    @Secured("ADMIN")
    public OfflineSelectionDto createOfflineSelection(@RequestBody @Valid
                                                      NewOfflineSelectionRequest newOfflineSelectionRequest) {
        log.info("Добавление администратором нового оффлайн-отбора {}.", newOfflineSelectionRequest);
        offlineSelectionValidationHelper.isNewOfflineSelectionDateValid(newOfflineSelectionRequest);
        return offlineSelectionService.addOfflineSelection(newOfflineSelectionRequest);
    }

    @DeleteMapping("/{selectionId}")
    @Secured("ADMIN")
    public void deleteOfflineSelection(@PathVariable @Positive Long selectionId) {
        log.info("Удаление администратором оффлайн-отбора с ИД {}.", selectionId);
        offlineSelectionService.deleteOfflineSelection(selectionId);
    }

    @PatchMapping("/{selectionId}")
    @Secured("ADMIN")
    public OfflineSelectionDto updateOfflineSelectionById(@PathVariable @Positive Long selectionId,
                                                          @RequestBody @Valid UpdateOfflineSelectionRequest request) {
        log.info("Обновление администратором оффлайн-отбора с ИД {}.", selectionId);
        return offlineSelectionService.updateOfflineSelectionById(selectionId, request);
    }

    @GetMapping("/{selectionId}/contestants")
    @Secured("ADMIN")
    public List<UserShortDto> getContestantsByOfflineSelectionId(@PathVariable @Positive Long selectionId,
                                                                 @RequestParam(defaultValue = "0") @Min(0) int from,
                                                                 @RequestParam(defaultValue = "10") @Positive int size) {
        log.info("Возвращение списка участников оффлайн-отбора с ИД {}.", selectionId);
        return userService.getContestantsByOfflineSelectionId(selectionId, from, size);
    }
}
