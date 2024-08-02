package com.kardoaward.kardo.event.repository;

import com.kardoaward.kardo.event.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}
