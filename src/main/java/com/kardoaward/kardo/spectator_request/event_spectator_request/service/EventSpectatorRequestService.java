package com.kardoaward.kardo.spectator_request.event_spectator_request.service;

import com.kardoaward.kardo.spectator_request.event_spectator_request.model.dto.EventSpectatorRequestDto;
import com.kardoaward.kardo.spectator_request.event_spectator_request.model.dto.NewEventSpectatorRequest;

public interface EventSpectatorRequestService {

    EventSpectatorRequestDto addEventSpectatorRequest(Long requestorId, NewEventSpectatorRequest request);
}
