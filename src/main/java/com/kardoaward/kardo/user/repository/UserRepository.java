package com.kardoaward.kardo.user.repository;

import com.kardoaward.kardo.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
