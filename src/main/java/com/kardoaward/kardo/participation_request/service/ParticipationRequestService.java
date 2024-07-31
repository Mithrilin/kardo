package com.kardoaward.kardo.participation_request.service;

import com.kardoaward.kardo.participation_request.model.dto.NewParticipationRequest;
import com.kardoaward.kardo.participation_request.model.dto.ParticipationRequestDto;

import java.util.List;

public interface ParticipationRequestService {

    ParticipationRequestDto addParticipation(Long requestorId, NewParticipationRequest newParticipationRequest);

    void deleteParticipationById(Long requestorId, Long participationId);

    ParticipationRequestDto getParticipationById(Long requestorId, Long participationId);

    ParticipationRequestDto getParticipationByIdByAdmin(Long participationId);

    List<ParticipationRequestDto> getParticipationsBySelectionId(Long selectionId, int from, int size);
}
