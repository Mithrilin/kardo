package com.kardoaward.kardo.selection.offline_selection.controller;

import com.kardoaward.kardo.selection.offline_selection.model.dto.OfflineSelectionDto;
import com.kardoaward.kardo.selection.offline_selection.service.OfflineSelectionService;
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
@RequestMapping("/selections/offline")
@Validated
public class OfflineSelectionController {

    private final OfflineSelectionService offlineSelectionService;

    @GetMapping("/{selectionId}")
    public OfflineSelectionDto getOfflineSelectionById(@PathVariable @Positive Long selectionId) {
        log.info("Возвращение оффлайн-отбора с ИД {}.", selectionId);
        return offlineSelectionService.getOfflineSelectionById(selectionId);
    }

    @GetMapping
    public List<OfflineSelectionDto> getOfflineSelections(@RequestParam(defaultValue = "0") @Min(0) int from,
                                                          @RequestParam(defaultValue = "10") @Positive int size) {
        log.info("Возвращение списка оффлайн-отборов.");
        return offlineSelectionService.getOfflineSelections(from, size);
    }
}
