package com.kardoaward.kardo.spectator_request.selection_spectator_request.service;

import com.kardoaward.kardo.spectator_request.selection_spectator_request.model.dto.NewSelectionSpectatorRequest;
import com.kardoaward.kardo.spectator_request.selection_spectator_request.model.dto.SelectionSpectatorRequestDto;

public interface SelectionSpectatorRequestService {

    SelectionSpectatorRequestDto addSelectionSpectatorRequest(Long requestorId, NewSelectionSpectatorRequest request);

    void deleteSelectionSpectatorRequestById(Long requestorId, Long spectatorId);

    SelectionSpectatorRequestDto getSelectionSpectatorRequestById(Long requestorId, Long spectatorId);
}
