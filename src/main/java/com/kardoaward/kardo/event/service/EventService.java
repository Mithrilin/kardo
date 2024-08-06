package com.kardoaward.kardo.event.service;

import com.kardoaward.kardo.event.model.dto.EventDto;
import com.kardoaward.kardo.event.model.dto.NewEventRequest;
import com.kardoaward.kardo.event.model.dto.UpdateEventRequest;
import com.kardoaward.kardo.event.model.params.EventRequestParams;

import java.util.List;

public interface EventService {

    EventDto addEvent(NewEventRequest newEventRequest);

    void deleteEventById(Long eventId);

    EventDto getEventById(Long eventId);

    List<EventDto> getEventsByParams(EventRequestParams eventRequestParams);

    EventDto updateEventById(Long eventId, UpdateEventRequest request);
}
