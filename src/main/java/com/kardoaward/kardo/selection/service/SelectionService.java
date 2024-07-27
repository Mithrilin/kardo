package com.kardoaward.kardo.selection.service;

import com.kardoaward.kardo.selection.model.dto.NewSelectionRequest;
import com.kardoaward.kardo.selection.model.dto.SelectionDto;

public interface SelectionService {

    SelectionDto addSelection(NewSelectionRequest newSelectionRequest);

    void deleteSelection(Long selectionId);

    SelectionDto getSelectionById(Long selectionId);
}
