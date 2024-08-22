package com.kardoaward.kardo.participation_request.service;

import com.kardoaward.kardo.participation_request.dto.NewParticipationRequest;
import com.kardoaward.kardo.participation_request.dto.ParticipationRequestDto;
import com.kardoaward.kardo.participation_request.dto.update.ParticipationRequestStatusUpdateRequest;
import com.kardoaward.kardo.participation_request.dto.update.ParticipationRequestStatusUpdateResult;
import com.kardoaward.kardo.participation_request.dto.update.UpdateParticipationRequest;
import com.kardoaward.kardo.user.model.User;

import java.util.List;

public interface ParticipationRequestService {

    ParticipationRequestDto addParticipation(User requestor, NewParticipationRequest newParticipationRequest);

    void deleteParticipationById(User requestor, Long participationId);

    ParticipationRequestDto getParticipationById(User requestor, Long participationId);

    List<ParticipationRequestDto> getParticipationsBySelectionId(Long selectionId, int from, int size);

    ParticipationRequestDto updateParticipationById(Long requestorId, Long participationId,
                                                    UpdateParticipationRequest request);

    ParticipationRequestStatusUpdateResult updateParticipationRequestStatus(
            Long selectionId, ParticipationRequestStatusUpdateRequest request);
}
