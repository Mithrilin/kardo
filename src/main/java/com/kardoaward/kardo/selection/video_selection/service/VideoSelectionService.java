package com.kardoaward.kardo.selection.video_selection.service;

import com.kardoaward.kardo.selection.video_selection.model.dto.UpdateVideoSelectionRequest;
import com.kardoaward.kardo.selection.video_selection.model.dto.NewVideoSelectionRequest;
import com.kardoaward.kardo.selection.video_selection.model.dto.VideoSelectionDto;

import java.util.List;

public interface VideoSelectionService {

    VideoSelectionDto addVideoSelection(NewVideoSelectionRequest newVideoSelectionRequest);

    void deleteVideoSelection(Long selectionId);

    VideoSelectionDto getVideoSelectionById(Long selectionId);

    List<VideoSelectionDto> getVideoSelections(int from, int size);

    VideoSelectionDto updateVideoSelectionById(Long selectionId, UpdateVideoSelectionRequest request);

    List<VideoSelectionDto> getVideoSelectionsByGrandCompetitionId(Long competitionId, int from, int size);
}
