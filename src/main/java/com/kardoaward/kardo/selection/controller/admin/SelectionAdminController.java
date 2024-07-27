package com.kardoaward.kardo.selection.controller.admin;

import com.kardoaward.kardo.exception.BadRequestException;
import com.kardoaward.kardo.selection.model.dto.NewSelectionRequest;
import com.kardoaward.kardo.selection.model.dto.SelectionDto;
import com.kardoaward.kardo.selection.model.dto.UpdateSelectionRequest;
import com.kardoaward.kardo.selection.service.SelectionService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping("/admin/selections")
@Validated
public class SelectionAdminController {

    private final SelectionService selectionService;

    @PostMapping
    public SelectionDto createSelection(@RequestBody @Valid NewSelectionRequest newSelectionRequest) {

        if (newSelectionRequest.getSelectionEnd().isBefore(newSelectionRequest.getSelectionStart())) {
            log.error("Дата и время начала отбора не может быть в после его конца.");
            throw new BadRequestException("Дата и время начала отбора не может быть в после его конца.");
        }

        log.info("Добавление администратором нового отбора {}.", newSelectionRequest);
        return selectionService.addSelection(newSelectionRequest);
    }

    @DeleteMapping("/{selectionId}")
    public void deleteSelection(@PathVariable @Positive Long selectionId) {
        log.info("Удаление администратором отбора с ИД {}.", selectionId);
        selectionService.deleteSelection(selectionId);
    }

    @PatchMapping("/{selectionId}")
    public SelectionDto updateSelectionById(@PathVariable @Positive Long selectionId,
                                            @RequestBody @Valid UpdateSelectionRequest request) {
        log.info("Обновление администратором отбора с ИД {}.", selectionId);
        return selectionService.updateSelectionById(selectionId, request);
    }
}
