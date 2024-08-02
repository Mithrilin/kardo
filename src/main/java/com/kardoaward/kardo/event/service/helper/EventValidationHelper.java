package com.kardoaward.kardo.event.service.helper;

import com.kardoaward.kardo.event.model.Event;
import com.kardoaward.kardo.event.repository.EventRepository;
import com.kardoaward.kardo.exception.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class EventValidationHelper {

    private final EventRepository eventRepository;

    public Event isEventPresent(Long eventId) {
        Optional<Event> optionalEvent = eventRepository.findById(eventId);

        if (optionalEvent.isEmpty()) {
            log.error("Мероприятие с ИД {} отсутствует в БД.", eventId);
            throw new NotFoundException(String.format("Мероприятие с ИД %d отсутствует в БД.", eventId));
        }

        return optionalEvent.get();
    }
}
