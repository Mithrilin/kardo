package com.kardoaward.kardo.spectator_request.selection_spectator_request.repository;

import com.kardoaward.kardo.spectator_request.selection_spectator_request.model.SelectionSpectatorRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SelectionSpectatorRequestRepository extends JpaRepository<SelectionSpectatorRequest, Long> {

    Page<SelectionSpectatorRequest> findBySelection_Id(Long selectionId, PageRequest pageRequest);
}
