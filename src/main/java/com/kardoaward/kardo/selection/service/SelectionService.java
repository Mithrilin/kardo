package com.kardoaward.kardo.selection.service;

import com.kardoaward.kardo.selection.model.dto.NewSelectionRequest;
import com.kardoaward.kardo.selection.model.dto.SelectionDto;
import com.kardoaward.kardo.selection.model.dto.UpdateSelectionRequest;

import java.util.List;

public interface SelectionService {

    SelectionDto addSelection(NewSelectionRequest newSelectionRequest);

    void deleteSelection(Long selectionId);

    SelectionDto getSelectionById(Long selectionId);

    List<SelectionDto> getSelections(int from, int size);

    SelectionDto updateSelectionById(Long selectionId, UpdateSelectionRequest request);

    List<SelectionDto> getSelectionsByRequestorId(Long requestorId, int from, int size);
}
