package com.kardoaward.kardo.participation_request.repository;

import com.kardoaward.kardo.participation_request.model.ParticipationRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipationRequestRepository extends JpaRepository<ParticipationRequest, Long> {

    Page<ParticipationRequest> findBySelection_Id(Long selectionId, PageRequest pageRequest);
}
