package com.kardoaward.kardo.spectator_request.event_spectator_request.repository;

import com.kardoaward.kardo.spectator_request.event_spectator_request.model.EventSpectatorRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventSpectatorRequestRepository extends JpaRepository<EventSpectatorRequest, Long> {
}
