package com.kardoaward.kardo.participation_request.service.helper;

import com.kardoaward.kardo.exception.NotFoundException;
import com.kardoaward.kardo.exception.NotValidException;
import com.kardoaward.kardo.participation_request.model.ParticipationRequest;
import com.kardoaward.kardo.participation_request.repository.ParticipationRequestRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class ParticipationRequestValidationHelper {

    private final ParticipationRequestRepository repository;

    public ParticipationRequest isParticipationRequestPresent(Long participationId) {
        Optional<ParticipationRequest> optionalParticipationRequest = repository.findById(participationId);

        if (optionalParticipationRequest.isEmpty()) {
            log.error("Заявка с ИД {} отсутствует в БД.", participationId);
            throw new NotFoundException(String.format("Заявка с ИД %d отсутствует в БД.", participationId));
        }

        return optionalParticipationRequest.get();
    }

    public void isUserRequester(Long userId, Long requestId) {
        if (!userId.equals(requestId)) {
            log.error("Пользователь с ИД {} не является создателем заявки с ИД {}.", userId, requestId);
            throw new NotValidException(String.format("Пользователь с ИД %d не является создателем заявки с ИД %d.",
                    userId, requestId));
        }
    }
}
