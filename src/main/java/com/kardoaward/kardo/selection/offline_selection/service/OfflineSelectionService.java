package com.kardoaward.kardo.selection.offline_selection.service;

import com.kardoaward.kardo.selection.offline_selection.model.dto.NewOfflineSelectionRequest;
import com.kardoaward.kardo.selection.offline_selection.model.dto.OfflineSelectionDto;
import com.kardoaward.kardo.selection.offline_selection.model.dto.UpdateOfflineSelectionRequest;

import java.util.List;

public interface OfflineSelectionService {

    OfflineSelectionDto addOfflineSelection(NewOfflineSelectionRequest newOfflineSelectionRequest);

    void deleteOfflineSelection(Long selectionId);

    OfflineSelectionDto getOfflineSelectionById(Long selectionId);

    List<OfflineSelectionDto> getOfflineSelections(int from, int size);

    OfflineSelectionDto updateOfflineSelectionById(Long selectionId, UpdateOfflineSelectionRequest request);

    List<OfflineSelectionDto> getOfflineSelectionsByRequestorId(Long requestorId, int from, int size);
}
