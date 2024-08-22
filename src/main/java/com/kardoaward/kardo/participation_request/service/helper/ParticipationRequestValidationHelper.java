package com.kardoaward.kardo.participation_request.service.helper;

import com.kardoaward.kardo.exception.NotFoundException;
import com.kardoaward.kardo.exception.NotValidException;
import com.kardoaward.kardo.participation_request.model.ParticipationRequest;
import com.kardoaward.kardo.participation_request.repository.ParticipationRequestRepository;
import com.kardoaward.kardo.user.model.User;
import com.kardoaward.kardo.user.enums.Role;
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

    public void isUserRequesterOrAdmin(User requestor, Long requesterId) {
        if (!requestor.getId().equals(requesterId) && requestor.getRole() != Role.ADMIN) {
            log.error("Пользователь с ИД {} не является создателем заявки с ИД {} или администратором.",
                    requestor.getId(), requesterId);
            throw new NotValidException(String.format("Пользователь с ИД %d не является создателем заявки с ИД %d " +
                            "или администратором.", requestor.getId(), requesterId));
        }
    }
}
