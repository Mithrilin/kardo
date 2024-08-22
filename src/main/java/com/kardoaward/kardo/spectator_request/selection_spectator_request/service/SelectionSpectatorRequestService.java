package com.kardoaward.kardo.spectator_request.selection_spectator_request.service;

import com.kardoaward.kardo.spectator_request.dto.update.SpectatorRequestStatusUpdateRequest;
import com.kardoaward.kardo.spectator_request.dto.update.SpectatorRequestStatusUpdateResult;
import com.kardoaward.kardo.spectator_request.selection_spectator_request.dto.NewSelectionSpectatorRequest;
import com.kardoaward.kardo.spectator_request.selection_spectator_request.dto.SelectionSpectatorRequestDto;
import com.kardoaward.kardo.user.model.User;

import java.util.List;

public interface SelectionSpectatorRequestService {

    SelectionSpectatorRequestDto addSelectionSpectatorRequest(User requestor, NewSelectionSpectatorRequest request);

    void deleteSelectionSpectatorRequestById(User requestor, Long spectatorId);

    SelectionSpectatorRequestDto getSelectionSpectatorRequestById(User requestor, Long spectatorId);

    List<SelectionSpectatorRequestDto> getSelectionSpectatorRequestBySelectionId(Long selectionId, int from, int size);

    SpectatorRequestStatusUpdateResult updateSelectionSpectatorRequestStatusBySelectionId(
            Long selectionId, SpectatorRequestStatusUpdateRequest request);
}
