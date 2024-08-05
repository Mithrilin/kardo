package com.kardoaward.kardo.selection.video_selection.repository;

import com.kardoaward.kardo.selection.video_selection.model.VideoSelection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoSelectionRepository extends JpaRepository<VideoSelection, Long> {

    Page<VideoSelection> findByCompetition_Id(Long competitionId, PageRequest pageRequest);
}
