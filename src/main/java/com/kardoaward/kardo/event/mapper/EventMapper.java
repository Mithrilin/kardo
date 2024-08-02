package com.kardoaward.kardo.event.mapper;

import com.kardoaward.kardo.event.model.Event;
import com.kardoaward.kardo.event.model.dto.EventDto;
import com.kardoaward.kardo.event.model.dto.NewEventRequest;
import com.kardoaward.kardo.offline_competition.mapper.OfflineCompetitionMapper;
import com.kardoaward.kardo.offline_competition.model.OfflineCompetition;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = OfflineCompetitionMapper.class)
public interface EventMapper {

    @Mapping(target = "id", constant = "0L")
    @Mapping(target = "status", constant = "UPCOMING")
    @Mapping(source = "newEventRequest.title", target = "title")
    @Mapping(source = "offlineCompetition", target = "competition")
    @Mapping(source = "newEventRequest.location", target = "location")
    @Mapping(source = "newEventRequest.fields", target = "fields")
    @Mapping(source = "newEventRequest.description", target = "description")
    Event newEventRequestToEvent(NewEventRequest newEventRequest, OfflineCompetition offlineCompetition);

    @Mapping(source = "returnedEvent.competition", target = "competitionDto")
    EventDto eventToEventDto(Event returnedEvent);
}
