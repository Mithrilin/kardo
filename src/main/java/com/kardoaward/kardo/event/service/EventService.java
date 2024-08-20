package com.kardoaward.kardo.event.service;

import com.kardoaward.kardo.event.dto.EventDto;
import com.kardoaward.kardo.event.dto.EventShortDto;
import com.kardoaward.kardo.event.dto.NewEventRequest;
import com.kardoaward.kardo.event.dto.UpdateEventRequest;
import com.kardoaward.kardo.event.model.params.EventRequestParams;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface EventService {

    EventDto addEvent(NewEventRequest newEventRequest);

    void deleteEventById(Long eventId);

    EventDto getEventById(Long eventId);

    List<EventShortDto> getEventsByParams(EventRequestParams eventRequestParams);

    EventDto updateEventById(Long eventId, UpdateEventRequest request);

    EventDto addLogoToEvent(Long eventId, MultipartFile file);
}
