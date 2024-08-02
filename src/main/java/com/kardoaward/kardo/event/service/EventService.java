package com.kardoaward.kardo.event.service;

import com.kardoaward.kardo.event.model.dto.EventDto;
import com.kardoaward.kardo.event.model.dto.NewEventRequest;

public interface EventService {

    EventDto addEvent(NewEventRequest newEventRequest);
}
