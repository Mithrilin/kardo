package com.kardoaward.kardo.spectator_request.event_spectator_request.service;

import com.kardoaward.kardo.spectator_request.event_spectator_request.dto.EventSpectatorRequestDto;
import com.kardoaward.kardo.spectator_request.event_spectator_request.dto.NewEventSpectatorRequest;
import com.kardoaward.kardo.spectator_request.dto.update.SpectatorRequestStatusUpdateRequest;
import com.kardoaward.kardo.spectator_request.dto.update.SpectatorRequestStatusUpdateResult;
import com.kardoaward.kardo.user.model.User;

import java.util.List;

public interface EventSpectatorRequestService {

    EventSpectatorRequestDto addEventSpectatorRequest(User requestor, NewEventSpectatorRequest request);

    void deleteEventSpectatorRequestById(User requestor, Long spectatorId);

    EventSpectatorRequestDto getEventSpectatorRequestById(User requestor, Long spectatorId);

    List<EventSpectatorRequestDto> getEventSpectatorRequestByEventId(Long eventId, int from, int size);

    SpectatorRequestStatusUpdateResult updateEventSpectatorRequestStatusByEventId(
            Long eventId, SpectatorRequestStatusUpdateRequest request);
}
