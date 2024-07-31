package com.kardoaward.kardo.participation_request.repository;

import com.kardoaward.kardo.participation_request.model.ParticipationRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipationRequestRepository extends JpaRepository<ParticipationRequest, Long> {
}
