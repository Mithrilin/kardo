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
import org.springframework.stereotype.Service;

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
}
