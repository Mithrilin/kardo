package com.kardoaward.kardo.selection.controller.admin;

import com.kardoaward.kardo.selection.model.dto.NewSelectionRequest;
import com.kardoaward.kardo.selection.model.dto.SelectionDto;
import com.kardoaward.kardo.selection.service.SelectionService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
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
        log.info("Добавление администратором нового отбора {}.", newSelectionRequest);
        return selectionService.addSelection(newSelectionRequest);
    }
}
