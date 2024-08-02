package com.kardoaward.kardo.selection.video_selection.repository;

import com.kardoaward.kardo.selection.video_selection.model.VideoSelection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface VideoSelectionRepository extends JpaRepository<VideoSelection, Long> {

    @Query(value =
            "SELECT * " +
            "FROM selections AS s " +
            "WHERE s.id IN (SELECT pr.selection_id " +
                           "FROM participation_requests AS pr " +
                           "WHERE pr.requester_id = :requestorId " +
                           "AND pr.status = 'CONFIRMED') " +
            "ORDER BY s.id", nativeQuery = true)
    Page<Selection> findAllByRequestorId(Long requestorId, PageRequest pageRequest);

    Page<VideoSelection> findByCompetition_Id(Long competitionId, PageRequest pageRequest);
}
