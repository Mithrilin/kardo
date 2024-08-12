package com.kardoaward.kardo.spectator_request.event_spectator_request.service.helper;

import com.kardoaward.kardo.exception.NotFoundException;
import com.kardoaward.kardo.exception.NotValidException;
import com.kardoaward.kardo.spectator_request.event_spectator_request.model.EventSpectatorRequest;
import com.kardoaward.kardo.spectator_request.event_spectator_request.repository.EventSpectatorRequestRepository;
import com.kardoaward.kardo.user.model.User;
import com.kardoaward.kardo.user.model.enums.Role;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class EventSpectatorRequestValidationHelper {

    private final EventSpectatorRequestRepository repository;

    public void isUserRequesterOrAdmin(User user, Long requesterId) {
        if (!user.getId().equals(requesterId) && user.getRole() != Role.ADMIN) {
            log.error("Пользователь с ИД {} не является создателем заявки зрителя мероприятия или администратором.",
                    user.getId());
            throw new NotValidException(String.format("Пользователь с ИД %d не является создателем заявки зрителя " +
                    "мероприятия или администратором.", user.getId()));
        }
    }

    public EventSpectatorRequest isSpectatorRequestPresent(Long spectatorId) {
        Optional<EventSpectatorRequest> optionalRequest = repository.findById(spectatorId);

        if (optionalRequest.isEmpty()) {
            log.error("Заявка зрителя мероприятия с ИД {} отсутствует в БД.", spectatorId);
            throw new NotFoundException(String.format("Заявка зрителя мероприятия с ИД %d отсутствует в БД.",
                    spectatorId));
        }

        return optionalRequest.get();
    }
}
