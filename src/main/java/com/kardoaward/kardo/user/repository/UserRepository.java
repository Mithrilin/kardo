package com.kardoaward.kardo.user.repository;

import com.kardoaward.kardo.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    Page<User> findByIdIn(List<Long> ids, PageRequest pageRequest);

    /* ToDo
        Написать SQL запрос.
     */
    Page<User> findAllBySelectionId(Long selectionId, PageRequest pageRequest);
}
