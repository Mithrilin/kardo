package com.kardoaward.kardo.user.repository;

import com.kardoaward.kardo.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    Page<User> findByIdIn(List<Long> ids, PageRequest pageRequest);

    @Query(value =
            "SELECT * " +
            "FROM users AS u " +
            "WHERE u.id IN (SELECT pr.requester_id " +
                           "FROM participation_requests AS pr " +
                           "WHERE pr.selection_id = :selectionId " +
                           "AND pr.status = 'CONFIRMED') " +
            "ORDER BY u.id", nativeQuery = true)
    Page<User> findAllBySelectionId(Long selectionId, PageRequest pageRequest);
}
