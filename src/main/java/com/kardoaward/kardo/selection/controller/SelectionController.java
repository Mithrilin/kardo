package com.kardoaward.kardo.selection.controller;

import com.kardoaward.kardo.selection.model.dto.SelectionDto;
import com.kardoaward.kardo.selection.service.SelectionService;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @GetMapping
    public List<SelectionDto> getSelections(@RequestParam(defaultValue = "0") @Min(0) int from,
                                           @RequestParam(defaultValue = "10") @Positive int size) {
        log.info("Получение списка отборов.");
        return selectionService.getSelections(from, size);
    }
}
