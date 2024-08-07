package com.kardoaward.kardo.spectator_request.event_spectator_request.service;

import com.kardoaward.kardo.spectator_request.event_spectator_request.model.dto.EventSpectatorRequestDto;
import com.kardoaward.kardo.spectator_request.event_spectator_request.model.dto.NewEventSpectatorRequest;
import com.kardoaward.kardo.spectator_request.model.dto.update.SpectatorRequestStatusUpdateRequest;
import com.kardoaward.kardo.spectator_request.model.dto.update.SpectatorRequestStatusUpdateResult;

import java.util.List;

public interface EventSpectatorRequestService {

    EventSpectatorRequestDto addEventSpectatorRequest(Long requestorId, NewEventSpectatorRequest request);

    void deleteEventSpectatorRequestById(Long requestorId, Long spectatorId);

    EventSpectatorRequestDto getEventSpectatorRequestById(Long requestorId, Long spectatorId);

    EventSpectatorRequestDto getEventSpectatorRequestByIdByAdmin(Long spectatorId);

    List<EventSpectatorRequestDto> getEventSpectatorRequestByEventId(Long eventId, int from, int size);

    SpectatorRequestStatusUpdateResult updateEventSpectatorRequestStatusByEventId(
            Long eventId, SpectatorRequestStatusUpdateRequest request);
}
