package com.kardoaward.kardo.user.service.helper;

import com.kardoaward.kardo.exception.NotFoundException;
import com.kardoaward.kardo.user.model.User;
import com.kardoaward.kardo.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class UserValidationHelper {

    private final UserRepository userRepository;

    public User isUserPresent(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isEmpty()) {
            log.error("Пользователь с ИД {} отсутствует в БД.", userId);
            throw new NotFoundException(String.format("Пользователь с ИД %d отсутствует в БД.", userId));
        }

        return optionalUser.get();
    }
}
