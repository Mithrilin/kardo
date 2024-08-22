package com.kardoaward.kardo.user.service.helper;

import com.kardoaward.kardo.exception.BadRequestException;
import com.kardoaward.kardo.exception.NotFoundException;
import com.kardoaward.kardo.user.model.User;
import com.kardoaward.kardo.user.enums.Role;
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

    public void isUserOwnerOrAdmin(User requestor, Long userId) {
        if (!userId.equals(requestor.getId()) && requestor.getRole() != Role.ADMIN) {
            log.error("Пользователь с ИД {} не является владельцем профиля пользователя с ИД {} или администратором.",
                    requestor.getId(), userId);
            throw new BadRequestException(String.format("Пользователь с ИД %d не является владельцем профиля " +
                    "пользователя с ИД %d или администратором.", requestor.getId(), userId));
        }
    }
}
