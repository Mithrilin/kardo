package com.kardoaward.kardo.spectator_request.event_spectator_request.repository;

import com.kardoaward.kardo.spectator_request.event_spectator_request.model.EventSpectatorRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventSpectatorRequestRepository extends JpaRepository<EventSpectatorRequest, Long> {

    Page<EventSpectatorRequest> findByEvent_Id(Long eventId, PageRequest pageRequest);
}
