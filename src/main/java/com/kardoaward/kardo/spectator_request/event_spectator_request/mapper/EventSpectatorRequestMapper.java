package com.kardoaward.kardo.spectator_request.event_spectator_request.mapper;

import com.kardoaward.kardo.event.model.Event;
import com.kardoaward.kardo.spectator_request.event_spectator_request.model.EventSpectatorRequest;
import com.kardoaward.kardo.user.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EventSpectatorRequestMapper {

    @Mapping(target = "id", constant = "0L")
    @Mapping(source = "user", target = "requester")
    @Mapping(source = "event", target = "event")
    EventSpectatorRequest newSpectatorRequestToSpectatorRequest(Event event, User user);
}
