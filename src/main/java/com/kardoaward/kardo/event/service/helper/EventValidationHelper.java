package com.kardoaward.kardo.event.service.helper;

import com.kardoaward.kardo.event.model.Event;
import com.kardoaward.kardo.event.model.dto.NewEventRequest;
import com.kardoaward.kardo.event.model.dto.UpdateEventRequest;
import com.kardoaward.kardo.event.repository.EventRepository;
import com.kardoaward.kardo.exception.BadRequestException;
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

    public void isUpdateEventDateValid(Event event, UpdateEventRequest request) {
        if (request.getEventStart() != null
                && request.getEventEnd() != null
                && request.getEventEnd().isBefore(request.getEventStart())) {
            log.error("Дата и время начала мероприятия не может быть после его конца.");
            throw new BadRequestException("Дата и время начала мероприятия не может быть после его конца.");
        }

        if (request.getEventStart() != null
                && request.getEventEnd() == null
                && request.getEventStart().isAfter(event.getEventEnd())) {
            log.error("Дата и время начала мероприятия не может быть после его конца.");
            throw new BadRequestException("Дата и время начала мероприятия не может быть после его конца.");
        }

        if (request.getEventStart() == null
                && request.getEventEnd() != null
                && request.getEventEnd().isBefore(event.getEventStart())) {
            log.error("Дата и время конца мероприятия не может быть раньше его начала.");
            throw new BadRequestException("Дата и время конца мероприятия не может быть раньше его начала.");
        }
    }

    public void isNewEventDateValid(NewEventRequest newEventRequest) {
        if (newEventRequest.getEventEnd().isBefore(newEventRequest.getEventStart())) {
            log.error("Дата и время начала мероприятия не может быть после его конца.");
            throw new BadRequestException("Дата и время начала мероприятия не может быть после его конца.");
        }
    }
}
