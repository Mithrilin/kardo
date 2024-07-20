package com.kardoaward.kardo.user.service;

import com.kardoaward.kardo.user.model.User;
import com.kardoaward.kardo.user.repository.UserRepository;
import com.kardoaward.kardo.user.service.helper.UserValidationHelper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserValidationHelper userValidationHelper;

    @Override
    @Transactional
    public User addUser(User user) {
        User returnedUser = userRepository.save(user);
        log.info("Пользователь с ID = {} создан.", returnedUser.getId());
        return returnedUser;
    }

    @Override
    public User getUserById(Long userId) {
        User user = userValidationHelper.isUserPresent(userId);
        log.info("Пользователь с ИД {} возвращен.", userId);
        return user;
    }

    @Override
    public void deleteUser(Long userId) {
        userValidationHelper.isUserPresent(userId);
        userRepository.deleteById(userId);
        log.info("Пользователь с ID {} удалён.", userId);
    }
}
