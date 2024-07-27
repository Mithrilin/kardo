package com.kardoaward.kardo.selection.controller;

import com.kardoaward.kardo.selection.model.dto.SelectionDto;
import com.kardoaward.kardo.selection.service.SelectionService;
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
@RequestMapping("/selections")
@Validated
public class SelectionController {

    private final SelectionService selectionService;

    @GetMapping("/{selectionId}")
    public SelectionDto getSelectionById(@PathVariable Long selectionId) {
        log.info("Получение отбора с ИД {}.", selectionId);
        return selectionService.getSelectionById(selectionId);
    }
}
