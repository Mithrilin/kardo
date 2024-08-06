package com.kardoaward.kardo.spectator_request.selection_spectator_request.service;

import com.kardoaward.kardo.spectator_request.model.dto.update.SpectatorRequestStatusUpdateRequest;
import com.kardoaward.kardo.spectator_request.model.dto.update.SpectatorRequestStatusUpdateResult;
import com.kardoaward.kardo.spectator_request.selection_spectator_request.model.dto.NewSelectionSpectatorRequest;
import com.kardoaward.kardo.spectator_request.selection_spectator_request.model.dto.SelectionSpectatorRequestDto;

import java.util.List;

public interface SelectionSpectatorRequestService {

    SelectionSpectatorRequestDto addSelectionSpectatorRequest(Long requestorId, NewSelectionSpectatorRequest request);

    void deleteSelectionSpectatorRequestById(Long requestorId, Long spectatorId);

    SelectionSpectatorRequestDto getSelectionSpectatorRequestById(Long requestorId, Long spectatorId);

    SelectionSpectatorRequestDto getSelectionSpectatorRequestByIdByAdmin(Long spectatorId);

    List<SelectionSpectatorRequestDto> getSelectionSpectatorRequestByEventId(Long selectionId, int from, int size);

    SpectatorRequestStatusUpdateResult updateSelectionSpectatorRequestStatusBySelectionId(
            Long selectionId, SpectatorRequestStatusUpdateRequest request);
}
