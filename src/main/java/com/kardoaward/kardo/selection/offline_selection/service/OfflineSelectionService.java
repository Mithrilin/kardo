package com.kardoaward.kardo.selection.offline_selection.service;

import com.kardoaward.kardo.selection.offline_selection.model.dto.NewOfflineSelectionRequest;
import com.kardoaward.kardo.selection.offline_selection.model.dto.OfflineSelectionDto;

import java.util.List;

public interface OfflineSelectionService {

    OfflineSelectionDto addOfflineSelection(NewOfflineSelectionRequest newOfflineSelectionRequest);

    void deleteOfflineSelection(Long selectionId);

    OfflineSelectionDto getOfflineSelectionById(Long selectionId);

    List<OfflineSelectionDto> getOfflineSelections(int from, int size);
}
