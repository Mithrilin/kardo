package com.kardoaward.kardo.spectator_request.event_spectator_request.service.helper;

import com.kardoaward.kardo.exception.NotValidException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class EventSpectatorRequestValidationHelper {

    public void isUserRequester(Long userId, Long requesterId) {
        if (!userId.equals(requesterId)) {
            log.error("Пользователь с ИД {} не является создателем заявки зрителя мероприятия.", userId);
            throw new NotValidException(String.format("Пользователь с ИД %d не является создателем заявки зрителя " +
                    "мероприятия.", userId));
        }
    }
}
