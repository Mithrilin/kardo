package com.kardoaward.kardo.participation_request.service;

import com.kardoaward.kardo.participation_request.model.dto.NewParticipationRequest;
import com.kardoaward.kardo.participation_request.model.dto.ParticipationRequestDto;

public interface ParticipationRequestService {

    ParticipationRequestDto addParticipation(Long requestorId, NewParticipationRequest newParticipationRequest);
}
