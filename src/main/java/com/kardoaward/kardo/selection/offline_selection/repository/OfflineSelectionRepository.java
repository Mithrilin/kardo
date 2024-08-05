package com.kardoaward.kardo.selection.offline_selection.repository;

import com.kardoaward.kardo.selection.offline_selection.model.OfflineSelection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OfflineSelectionRepository extends JpaRepository<OfflineSelection, Long> {

    @Query(value =
            "SELECT * " +
            "FROM offline_selections AS s " +
            "WHERE s.id IN (SELECT pr.selection_id " +
                           "FROM participation_requests AS pr " +
                           "WHERE pr.requester_id = :requestorId " +
                           "AND pr.status = 'CONFIRMED') " +
            "ORDER BY s.id", nativeQuery = true)
    Page<OfflineSelection> findAllByRequestorId(Long requestorId, PageRequest pageRequest);

    Page<OfflineSelection> findByCompetition_Id(Long competitionId, PageRequest pageRequest);
}
