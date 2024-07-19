package com.kardoaward.kardo.user.service;

import com.kardoaward.kardo.user.model.User;
import com.kardoaward.kardo.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public User addUser(User user) {
        User returnedUser = userRepository.save(user);
        log.info("Пользователь с ID = {} создан.", returnedUser.getId());
        return returnedUser;
    }
}
