package com.kardoaward.kardo.event.mapper;

import com.kardoaward.kardo.event.model.Event;
import com.kardoaward.kardo.event.dto.EventDto;
import com.kardoaward.kardo.event.dto.EventShortDto;
import com.kardoaward.kardo.event.dto.NewEventRequest;
import com.kardoaward.kardo.event.dto.UpdateEventRequest;
import com.kardoaward.kardo.grand_competition.mapper.GrandCompetitionMapper;
import com.kardoaward.kardo.grand_competition.model.GrandCompetition;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring", uses = GrandCompetitionMapper.class)
public interface EventMapper {

    @Mapping(target = "id", constant = "0L")
    @Mapping(target = "status", constant = "UPCOMING")
    @Mapping(source = "newEventRequest.title", target = "title")
    @Mapping(source = "grandCompetition", target = "competition")
    @Mapping(source = "newEventRequest.location", target = "location")
    @Mapping(source = "newEventRequest.fields", target = "fields")
    @Mapping(source = "newEventRequest.description", target = "description")
    Event newEventRequestToEvent(NewEventRequest newEventRequest, GrandCompetition grandCompetition);

    @Mapping(source = "event.competition", target = "competitionDto")
    @Mapping(source = "event.logo.filePath", target = "logo")
    EventDto eventToEventDto(Event event);

    @Mapping(source = "event.competition", target = "competitionDto")
    @Mapping(source = "event.logo.filePath", target = "logo")
    EventShortDto eventToEventShortDto(Event event);

    @BeanMapping(nullValuePropertyMappingStrategy = IGNORE)
    void updateEvent(UpdateEventRequest request, @MappingTarget Event event);

    List<EventShortDto> eventListToEventShortDtoList(List<Event> events);
}
