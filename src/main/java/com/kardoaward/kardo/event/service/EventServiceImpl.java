package com.kardoaward.kardo.event.service;

import com.kardoaward.kardo.event.mapper.EventMapper;
import com.kardoaward.kardo.event.model.Event;
import com.kardoaward.kardo.event.dto.EventDto;
import com.kardoaward.kardo.event.dto.EventShortDto;
import com.kardoaward.kardo.event.dto.NewEventRequest;
import com.kardoaward.kardo.event.dto.UpdateEventRequest;
import com.kardoaward.kardo.event.model.params.EventRequestParams;
import com.kardoaward.kardo.event.repository.EventRepository;
import com.kardoaward.kardo.event.service.helper.EventValidationHelper;
import com.kardoaward.kardo.event.service.specification.EventSpecifications;
import com.kardoaward.kardo.exception.FileContentException;
import com.kardoaward.kardo.grand_competition.model.GrandCompetition;
import com.kardoaward.kardo.grand_competition.service.helper.GrandCompetitionValidationHelper;
import com.kardoaward.kardo.media_file.FileManager;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    private final EventMapper eventMapper;

    private final EventValidationHelper eventValidationHelper;
    private final GrandCompetitionValidationHelper grandHelper;

    private final FileManager fileManager;

    private final String FOLDER_PATH;

    public EventServiceImpl(EventRepository eventRepository,
                            EventMapper eventMapper,
                            EventValidationHelper eventValidationHelper,
                            GrandCompetitionValidationHelper grandHelper,
                            FileManager fileManager,
                            @Value("${folder.path}") String FOLDER_PATH) {
        this.eventRepository = eventRepository;
        this.eventMapper = eventMapper;
        this.eventValidationHelper = eventValidationHelper;
        this.grandHelper = grandHelper;
        this.fileManager = fileManager;
        this.FOLDER_PATH = FOLDER_PATH;
    }

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
    public EventDto addLogoToEvent(Long eventId, MultipartFile file) {
        Event event = eventValidationHelper.isEventPresent(eventId);
        String oldLogoPath = event.getLogo();
        String path = FOLDER_PATH + "/events/" + event.getId() + "/logo/";
        event.setLogo(path + file.getOriginalFilename());
        Event updatedEvent = eventRepository.save(event);
        EventDto eventDto = eventMapper.eventToEventDto(updatedEvent);
        fileManager.addLogoToEvent(oldLogoPath, file, path);
        log.info("Логотип к мероприятию с ID = {} добавлен.", eventDto.getId());
        return eventDto;
    }

    @Override
    @Transactional
    public void deleteLogoFromEvent(Long eventId) {
        Event event = eventValidationHelper.isEventPresent(eventId);

        if (event.getLogo() == null) {
            log.error("У мероприятия с ИД {} отсутствует логотип.", event.getId());
            throw new FileContentException(String.format("У мероприятия с ИД %d отсутствует логотип.", event.getId()));
        }

        event.setLogo(null);
        eventRepository.save(event);
        String path = FOLDER_PATH + "/events/" + event.getId() + "/logo/";
        fileManager.deleteFileOrDirectory(path);
        log.info("Логотип мероприятия с ID {} удалён.", event.getId());
    }

    @Override
    @Transactional
    public void deleteEventById(Long eventId) {
        eventValidationHelper.isEventPresent(eventId);
        File eventPath = new File(FOLDER_PATH + "/events/" + eventId);

        try {
            FileUtils.deleteDirectory(eventPath);
        } catch (IOException e) {
            throw new FileContentException("Не удалось удалить директорию: " + eventPath.getPath());
        }

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
    public List<EventShortDto> getEventsByParams(EventRequestParams params) {
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
        List<EventShortDto> eventShortDtos = eventMapper.eventListToEventShortDtoList(events);
        log.info("Список мероприятий с номера {} размером {} возвращён.", params.getFrom(), eventShortDtos.size());
        return eventShortDtos;
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
}
