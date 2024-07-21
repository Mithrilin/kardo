package com.kardoaward.kardo.user.service;

import com.kardoaward.kardo.user.model.User;
import com.kardoaward.kardo.user.repository.UserRepository;
import com.kardoaward.kardo.user.service.helper.UserValidationHelper;
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
    @Transactional
    public void deleteUser(Long userId) {
        userValidationHelper.isUserPresent(userId);
        userRepository.deleteById(userId);
        log.info("Пользователь с ID {} удалён.", userId);
    }

    @Override
    //ToDo Нужен ли такой метод? Если нет, то переделать под получение всех пользователей
    public List<User> getUsersByIds(List<Long> ids, int from, int size) {
        int page = from / size;
        //ToDo По какому параметру сортируем?
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        Page<User> usersPage;

        if (ids == null) {
            usersPage = userRepository.findAll(pageRequest);
        } else {
            usersPage = userRepository.findByIdIn(ids, pageRequest);
        }
        if (usersPage.isEmpty()) {
            log.info("Не нашлось пользователей по заданным параметрам.");
            return new ArrayList<>();
        }

        List<User> users = usersPage.getContent();
        log.info("Список пользователей с номера {} размером {} возвращён.", from, users.size());
        return users;
    }
}
