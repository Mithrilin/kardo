package com.kardoaward.kardo.spectator_request.event_spectator_request.mapper;

import com.kardoaward.kardo.event.mapper.EventMapper;
import com.kardoaward.kardo.event.model.Event;
import com.kardoaward.kardo.spectator_request.event_spectator_request.model.EventSpectatorRequest;
import com.kardoaward.kardo.spectator_request.event_spectator_request.dto.EventSpectatorRequestDto;
import com.kardoaward.kardo.user.mapper.UserMapper;
import com.kardoaward.kardo.user.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {UserMapper.class, EventMapper.class})
public interface EventSpectatorRequestMapper {

    @Mapping(target = "id", constant = "0L")
    @Mapping(target = "status", constant = "PENDING")
    @Mapping(source = "user", target = "requester")
    @Mapping(source = "event", target = "event")
    EventSpectatorRequest newSpectatorRequestToSpectatorRequest(Event event, User user);

    @Mapping(source = "spectatorRequest.event", target = "eventDto")
    @Mapping(source = "spectatorRequest.requester", target = "requesterDto")
    EventSpectatorRequestDto spectatorRequestToSpectatorRequestDto(EventSpectatorRequest spectatorRequest);

    List<EventSpectatorRequestDto> spectatorRequestListToSpectatorRequestDtoList(List<EventSpectatorRequest> spectatorRequests);
}
