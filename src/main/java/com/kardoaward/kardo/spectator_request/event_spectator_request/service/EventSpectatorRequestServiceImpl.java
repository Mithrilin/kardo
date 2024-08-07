package com.kardoaward.kardo.spectator_request.event_spectator_request.service;

import com.kardoaward.kardo.event.model.Event;
import com.kardoaward.kardo.event.service.helper.EventValidationHelper;
import com.kardoaward.kardo.spectator_request.event_spectator_request.mapper.EventSpectatorRequestMapper;
import com.kardoaward.kardo.spectator_request.event_spectator_request.model.EventSpectatorRequest;
import com.kardoaward.kardo.spectator_request.event_spectator_request.model.dto.EventSpectatorRequestDto;
import com.kardoaward.kardo.spectator_request.event_spectator_request.model.dto.NewEventSpectatorRequest;
import com.kardoaward.kardo.spectator_request.event_spectator_request.repository.EventSpectatorRequestRepository;
import com.kardoaward.kardo.spectator_request.event_spectator_request.service.helper.EventSpectatorRequestValidationHelper;
import com.kardoaward.kardo.user.model.User;
import com.kardoaward.kardo.user.service.helper.UserValidationHelper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class EventSpectatorRequestServiceImpl implements EventSpectatorRequestService {

    private final EventSpectatorRequestRepository repository;

    private final EventSpectatorRequestMapper mapper;

    private final UserValidationHelper userValidationHelper;
    private final EventValidationHelper eventValidationHelper;
    private final EventSpectatorRequestValidationHelper helper;

    @Override
    @Transactional
    public EventSpectatorRequestDto addEventSpectatorRequest(Long requestorId, NewEventSpectatorRequest request) {
        User user = userValidationHelper.isUserPresent(requestorId);
        Event event = eventValidationHelper.isEventPresent(request.getEventId());
        EventSpectatorRequest eventSpectatorRequest = mapper.newSpectatorRequestToSpectatorRequest(event, user);
        EventSpectatorRequest returnedSpectatorRequest = repository.save(eventSpectatorRequest);
        EventSpectatorRequestDto spectatorRequestDto = mapper
                .spectatorRequestToSpectatorRequestDto(returnedSpectatorRequest);
        log.info("Заявка зрителя мероприятия с ИД {} пользователя с ИД {} создана.", spectatorRequestDto.getId(),
                requestorId);
        return spectatorRequestDto;
    }

    @Override
    @Transactional
    public void deleteEventSpectatorRequestById(Long requestorId, Long spectatorId) {
        userValidationHelper.isUserPresent(requestorId);
        EventSpectatorRequest eventSpectatorRequest = helper.isSpectatorRequestPresent(spectatorId);
        helper.isUserRequester(requestorId, eventSpectatorRequest.getRequester().getId());
        repository.deleteById(spectatorId);
        log.info("Заявка зрителя мероприятия с ИД {} пользователя с ИД {} удалена.", spectatorId, requestorId);
    }

    @Override
    public EventSpectatorRequestDto getEventSpectatorRequestById(Long requestorId, Long spectatorId) {
        userValidationHelper.isUserPresent(requestorId);
        EventSpectatorRequest eventSpectatorRequest = helper.isSpectatorRequestPresent(spectatorId);
        helper.isUserRequester(requestorId, eventSpectatorRequest.getRequester().getId());
        EventSpectatorRequestDto spectatorRequestDto = mapper
                .spectatorRequestToSpectatorRequestDto(eventSpectatorRequest);
        log.info("Заявка зрителя мероприятия с ИД {} пользователя с ИД {} возвращена.", spectatorId, requestorId);
        return spectatorRequestDto;
    }

    @Override
    public EventSpectatorRequestDto getEventSpectatorRequestByIdByAdmin(Long spectatorId) {
        EventSpectatorRequest eventSpectatorRequest = helper.isSpectatorRequestPresent(spectatorId);
        EventSpectatorRequestDto spectatorRequestDto = mapper
                .spectatorRequestToSpectatorRequestDto(eventSpectatorRequest);
        log.info("Заявка зрителя мероприятия с ИД {} возвращена.", spectatorId);
        return spectatorRequestDto;
    }

    @Override
    public List<EventSpectatorRequestDto> getEventSpectatorRequestByEventId(Long eventId, int from, int size) {
        eventValidationHelper.isEventPresent(eventId);
        int page = from / size;
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        Page<EventSpectatorRequest> spectatorRequestsPage = repository.findByEvent_Id(eventId, pageRequest);

        if (spectatorRequestsPage.isEmpty()) {
            log.info("Не нашлось заявок зрителей мероприятий по заданным параметрам.");
            return new ArrayList<>();
        }

        List<EventSpectatorRequest> spectatorRequests = spectatorRequestsPage.getContent();
        List<EventSpectatorRequestDto> spectatorRequestDtos = mapper
                .spectatorRequestListToSpectatorRequestDtoList(spectatorRequests);
        log.info("Список заявок зрителей к мероприятию с ИД {} с номера {} размером {} возвращён.", eventId, from,
                spectatorRequestDtos.size());
        return spectatorRequestDtos;
    }
}
