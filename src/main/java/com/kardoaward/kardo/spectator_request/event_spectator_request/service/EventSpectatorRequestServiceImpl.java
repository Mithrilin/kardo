package com.kardoaward.kardo.spectator_request.event_spectator_request.service;

import com.kardoaward.kardo.enums.RequestStatus;
import com.kardoaward.kardo.enums.UpdateRequestStatus;
import com.kardoaward.kardo.event.model.Event;
import com.kardoaward.kardo.event.service.helper.EventValidationHelper;
import com.kardoaward.kardo.spectator_request.event_spectator_request.mapper.EventSpectatorRequestMapper;
import com.kardoaward.kardo.spectator_request.event_spectator_request.model.EventSpectatorRequest;
import com.kardoaward.kardo.spectator_request.event_spectator_request.dto.EventSpectatorRequestDto;
import com.kardoaward.kardo.spectator_request.event_spectator_request.dto.NewEventSpectatorRequest;
import com.kardoaward.kardo.spectator_request.event_spectator_request.repository.EventSpectatorRequestRepository;
import com.kardoaward.kardo.spectator_request.event_spectator_request.service.helper.EventSpectatorRequestValidationHelper;
import com.kardoaward.kardo.spectator_request.dto.update.SpectatorRequestStatusUpdateRequest;
import com.kardoaward.kardo.spectator_request.dto.update.SpectatorRequestStatusUpdateResult;
import com.kardoaward.kardo.user.model.User;
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

    private final EventValidationHelper eventValidationHelper;
    private final EventSpectatorRequestValidationHelper helper;

    @Override
    @Transactional
    public EventSpectatorRequestDto addEventSpectatorRequest(User requestor, NewEventSpectatorRequest request) {
        Event event = eventValidationHelper.isEventPresent(request.getEventId());
        EventSpectatorRequest eventSpectatorRequest = mapper.newSpectatorRequestToSpectatorRequest(event, requestor);
        EventSpectatorRequest returnedSpectatorRequest = repository.save(eventSpectatorRequest);
        EventSpectatorRequestDto spectatorRequestDto = mapper
                .spectatorRequestToSpectatorRequestDto(returnedSpectatorRequest);
        log.info("Заявка зрителя мероприятия с ИД {} пользователя с ИД {} создана.", spectatorRequestDto.getId(),
                requestor.getId());
        return spectatorRequestDto;
    }

    @Override
    @Transactional
    public void deleteEventSpectatorRequestById(User requestor, Long spectatorId) {
        EventSpectatorRequest eventSpectatorRequest = helper.isSpectatorRequestPresent(spectatorId);
        helper.isUserRequesterOrAdmin(requestor, eventSpectatorRequest.getRequester().getId());
        repository.deleteById(spectatorId);
        log.info("Заявка зрителя мероприятия с ИД {} пользователя с ИД {} удалена.", spectatorId, requestor.getId());
    }

    @Override
    public EventSpectatorRequestDto getEventSpectatorRequestById(User requestor, Long spectatorId) {
        EventSpectatorRequest eventSpectatorRequest = helper.isSpectatorRequestPresent(spectatorId);
        helper.isUserRequesterOrAdmin(requestor, eventSpectatorRequest.getRequester().getId());
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

    @Override
    public SpectatorRequestStatusUpdateResult updateEventSpectatorRequestStatusByEventId(
            Long eventId, SpectatorRequestStatusUpdateRequest request) {

        eventValidationHelper.isEventPresent(eventId);
        List<Long> ids = request.getRequestIds();
        List<EventSpectatorRequest> spectatorRequests = repository.findAllById(ids);
        SpectatorRequestStatusUpdateResult result = new SpectatorRequestStatusUpdateResult();
        List<EventSpectatorRequest> updatedRequests = new ArrayList<>();

        for (EventSpectatorRequest spectatorRequest : spectatorRequests) {
            EventSpectatorRequestDto spectatorRequestDto = mapper
                    .spectatorRequestToSpectatorRequestDto(spectatorRequest);

            if (spectatorRequest.getStatus() == RequestStatus.PENDING) {

                if (request.getStatus() == UpdateRequestStatus.CONFIRMED) {
                    spectatorRequest.setStatus(RequestStatus.CONFIRMED);
                } else {
                    spectatorRequest.setStatus(RequestStatus.CANCELED);
                }
                updatedRequests.add(spectatorRequest);
                result.getUpdatedRequests().add(spectatorRequestDto);
            } else {
                result.getNotUpdatedRequests().add(spectatorRequestDto);
            }
        }

        repository.saveAll(updatedRequests);
        log.info("Статуса заявок зрителей к мероприятию с ИД {} обновился у {} заявок и не обновился у {} заявок.",
                eventId, result.getUpdatedRequests().size(), result.getNotUpdatedRequests().size());
        return result;
    }
}
