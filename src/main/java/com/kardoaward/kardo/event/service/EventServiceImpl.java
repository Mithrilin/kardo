package com.kardoaward.kardo.event.service;

import com.kardoaward.kardo.event.mapper.EventMapper;
import com.kardoaward.kardo.event.model.Event;
import com.kardoaward.kardo.event.model.dto.EventDto;
import com.kardoaward.kardo.event.model.dto.NewEventRequest;
import com.kardoaward.kardo.event.model.dto.UpdateEventRequest;
import com.kardoaward.kardo.event.model.params.EventRequestParams;
import com.kardoaward.kardo.event.repository.EventRepository;
import com.kardoaward.kardo.event.service.helper.EventValidationHelper;
import com.kardoaward.kardo.event.service.specification.EventSpecifications;
import com.kardoaward.kardo.exception.FileContentException;
import com.kardoaward.kardo.grand_competition.model.GrandCompetition;
import com.kardoaward.kardo.grand_competition.service.helper.GrandCompetitionValidationHelper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@AllArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    private final EventMapper eventMapper;

    private final EventValidationHelper eventValidationHelper;
    private final GrandCompetitionValidationHelper grandHelper;

    private final String FOLDER_PATH = "C:/Users/Roman/Desktop/test/events/";

    @Override
    @Transactional
    public EventDto addEvent(NewEventRequest newEventRequest) {
        GrandCompetition grandCompetition = grandHelper.isGrandCompetitionPresent(newEventRequest.getCompetitionId());
        Event event = eventMapper.newEventRequestToEvent(newEventRequest, grandCompetition);
        Event returnedEvent = eventRepository.save(event);
        EventDto eventDto = eventMapper.eventToEventDto(returnedEvent);
        log.info("Мероприятие с ID = {} создано.", eventDto.getId());
        return eventDto;
    }

    @Override
    @Transactional
    public void deleteEventById(Long eventId) {
        eventValidationHelper.isEventPresent(eventId);
        eventRepository.deleteById(eventId);
        log.info("Мероприятие с ID {} удалено.", eventId);
    }

    @Override
    public EventDto getEventById(Long eventId) {
        Event event = eventValidationHelper.isEventPresent(eventId);
        EventDto eventDto = eventMapper.eventToEventDto(event);
        log.info("Мероприятие с ИД {} возвращено.", eventId);
        return eventDto;
    }

    @Override
    public List<EventDto> getEventsByParams(EventRequestParams params) {
        PageRequest pageRequest = params.getPageRequest();
        List<Specification<Event>> specifications = EventSpecifications.searchEventFilterToSpecifications(params);
        Page<Event> eventPage = eventRepository.findAll(
                Objects.requireNonNull(specifications
                        .stream()
                        .reduce(Specification::and)
                        .orElse(null)),
                pageRequest);

        if (eventPage.isEmpty()) {
            log.info("Не нашлось мероприятий по заданным параметрам.");
            return new ArrayList<>();
        }

        List<Event> events = eventPage.getContent();
        List<EventDto> eventDtos = eventMapper.eventListToEventDtoList(events);
        log.info("Список мероприятий с номера {} размером {} возвращён.", params.getFrom(), eventDtos.size());
        return eventDtos;
    }

    @Override
    @Transactional
    public EventDto updateEventById(Long eventId, UpdateEventRequest request) {
        Event event = eventValidationHelper.isEventPresent(eventId);
        eventValidationHelper.isUpdateEventDateValid(event, request);
        eventMapper.updateEvent(request, event);
        Event updatedEvent = eventRepository.save(event);
        EventDto eventDto = eventMapper.eventToEventDto(updatedEvent);
        log.info("Мероприятие с ID {} обновлено.", eventId);
        return eventDto;
    }

    @Override
    public void uploadLogo(Long eventId, MultipartFile file) {
        Event event = eventValidationHelper.isEventPresent(eventId);
        String path = FOLDER_PATH + eventId + "/logo/";
        File oldLogoPath = new File(path);
        oldLogoPath.mkdirs();

        try {
            FileUtils.cleanDirectory(oldLogoPath);
        } catch (IOException e) {
            throw new FileContentException("Не удалось очистить директорию: " + oldLogoPath.getPath());
        }

        String logoPath = path + file.getOriginalFilename();

        try {
            file.transferTo(new File(logoPath));
        } catch (IOException e) {
            throw new FileContentException("Не удалось сохранить файл: " + logoPath);
        }

        event.setLogo(logoPath);
        eventRepository.save(event);
        log.info("Логотип мероприятия с ИД {} успешно добавлен.", eventId);
    }

    @Override
    public void deleteLogo(Long eventId) {
        Event event = eventValidationHelper.isEventPresent(eventId);
        File oldLogoPath = new File(FOLDER_PATH + eventId + "/logo/");

        try {
            FileUtils.cleanDirectory(oldLogoPath);
        } catch (IOException e) {
            throw new FileContentException("Не удалось очистить директорию: " + oldLogoPath.getPath());
        }

        event.setLogo(null);
        eventRepository.save(event);
        log.info("Логотип мероприятия с ИД {} успешно удалён.", eventId);
    }

    @Override
    public byte[] downloadLogoByEventId(Long eventId) {
        eventValidationHelper.isEventPresent(eventId);
        File logoPath = new File(FOLDER_PATH + eventId + "/logo/");
        File[] files = logoPath.listFiles();

        if (files == null) {
            return null;
        }

        File file = files[0];

        try {
            return Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            throw new FileContentException("Не удалось обработать файл.");
        }
    }
}
