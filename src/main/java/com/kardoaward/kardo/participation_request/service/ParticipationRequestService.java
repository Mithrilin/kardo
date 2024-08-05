package com.kardoaward.kardo.participation_request.service;

import com.kardoaward.kardo.participation_request.model.dto.NewParticipationRequest;
import com.kardoaward.kardo.participation_request.model.dto.ParticipationRequestDto;
import com.kardoaward.kardo.participation_request.model.dto.update.ParticipationRequestStatusUpdateRequest;
import com.kardoaward.kardo.participation_request.model.dto.update.ParticipationRequestStatusUpdateResult;
import com.kardoaward.kardo.participation_request.model.dto.update.UpdateParticipationRequest;

import java.util.List;

public interface ParticipationRequestService {

    ParticipationRequestDto addParticipation(Long requestorId, NewParticipationRequest newParticipationRequest);

    void deleteParticipationById(Long requestorId, Long participationId);

    ParticipationRequestDto getParticipationById(Long requestorId, Long participationId);

    ParticipationRequestDto getParticipationByIdByAdmin(Long participationId);

    List<ParticipationRequestDto> getParticipationsBySelectionId(Long selectionId, int from, int size);

    ParticipationRequestDto updateParticipationById(Long requestorId, Long participationId,
                                                    UpdateParticipationRequest request);

    ParticipationRequestStatusUpdateResult updateParticipationRequestStatusById(
            Long selectionId, ParticipationRequestStatusUpdateRequest request);
}
