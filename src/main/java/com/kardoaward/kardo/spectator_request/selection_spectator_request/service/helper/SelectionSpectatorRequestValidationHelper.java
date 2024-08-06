package com.kardoaward.kardo.spectator_request.selection_spectator_request.service.helper;

import com.kardoaward.kardo.exception.NotFoundException;
import com.kardoaward.kardo.exception.NotValidException;
import com.kardoaward.kardo.spectator_request.selection_spectator_request.model.SelectionSpectatorRequest;
import com.kardoaward.kardo.spectator_request.selection_spectator_request.repository.SelectionSpectatorRequestRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class SelectionSpectatorRequestValidationHelper {

    private final SelectionSpectatorRequestRepository repository;

    public void isUserRequester(Long userId, Long requesterId) {
        if (!userId.equals(requesterId)) {
            log.error("Пользователь с ИД {} не является создателем заявки зрителя отбора.", userId);
            throw new NotValidException(String.format("Пользователь с ИД %d не является создателем заявки зрителя " +
                    "отбора.", userId));
        }
    }

    public SelectionSpectatorRequest isSpectatorRequestPresent(Long spectatorId) {
        Optional<SelectionSpectatorRequest> optionalRequest = repository.findById(spectatorId);

        if (optionalRequest.isEmpty()) {
            log.error("Заявка зрителя отбора с ИД {} отсутствует в БД.", spectatorId);
            throw new NotFoundException(String.format("Заявка зрителя отбора с ИД %d отсутствует в БД.", spectatorId));
        }

        return optionalRequest.get();
    }
}
