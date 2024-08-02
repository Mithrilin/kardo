package com.kardoaward.kardo.selection.video_selection.service;

import com.kardoaward.kardo.grand_competition.model.GrandCompetition;
import com.kardoaward.kardo.grand_competition.service.helper.GrandCompetitionValidationHelper;
import com.kardoaward.kardo.selection.video_selection.mapper.VideoSelectionMapper;
import com.kardoaward.kardo.selection.model.dto.UpdateSelectionRequest;
import com.kardoaward.kardo.selection.video_selection.model.VideoSelection;
import com.kardoaward.kardo.selection.video_selection.model.dto.NewVideoSelectionRequest;
import com.kardoaward.kardo.selection.video_selection.model.dto.VideoSelectionDto;
import com.kardoaward.kardo.selection.video_selection.repository.VideoSelectionRepository;
import com.kardoaward.kardo.selection.video_selection.service.helper.VideoSelectionValidationHelper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class VideoSelectionServiceImpl implements VideoSelectionService {

    private final VideoSelectionRepository videoSelectionRepository;

    private final VideoSelectionMapper videoSelectionMapper;

    private final VideoSelectionValidationHelper videoSelectionValidationHelper;
    private final GrandCompetitionValidationHelper grandCompetitionValidationHelper;

    @Override
    @Transactional
    public VideoSelectionDto addVideoSelection(NewVideoSelectionRequest newVideoSelectionRequest) {
        GrandCompetition competition = grandCompetitionValidationHelper
                .isGrandCompetitionPresent(newVideoSelectionRequest.getCompetitionId());
        VideoSelection videoSelection = videoSelectionMapper
                .newVideoSelectionRequestToVideoSelection(newVideoSelectionRequest, competition);
        VideoSelection returnedVideoSelection = videoSelectionRepository.save(videoSelection);
        VideoSelectionDto videoSelectionDto = videoSelectionMapper
                .videoSelectionToVideoSelectionDto(returnedVideoSelection);
        log.info("Видео-отбор с ID = {} создан.", videoSelectionDto.getId());
        return videoSelectionDto;
    }

    @Override
    @Transactional
    public void deleteVideoSelection(Long selectionId) {
        videoSelectionValidationHelper.isVideoSelectionPresent(selectionId);
        videoSelectionRepository.deleteById(selectionId);
        log.info("Видео-отбор с ID {} удалён.", selectionId);
    }

    @Override
    public VideoSelectionDto getVideoSelectionById(Long selectionId) {
        VideoSelection videoSelection = videoSelectionValidationHelper.isVideoSelectionPresent(selectionId);
        VideoSelectionDto videoSelectionDto = videoSelectionMapper.videoSelectionToVideoSelectionDto(videoSelection);
        log.info("Видео-отбор с ИД {} возвращен.", selectionId);
        return videoSelectionDto;
    }

    @Override
    public List<VideoSelectionDto> getVideoSelections(int from, int size) {
        int page = from / size;
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        Page<VideoSelection> selectionsPage = videoSelectionRepository.findAll(pageRequest);

        if (selectionsPage.isEmpty()) {
            log.info("Не нашлось видео-отборов по заданным параметрам.");
            return new ArrayList<>();
        }

        List<VideoSelection> videoSelections = selectionsPage.getContent();
        List<VideoSelectionDto> videoSelectionDtos = videoSelectionMapper
                .videoSelectionListToVideoSelectionDtoList(videoSelections);
        log.info("Список видео-отборов с номера {} размером {} возвращён.", from, videoSelectionDtos.size());
        return videoSelectionDtos;
    }

    @Override
    @Transactional
    public VideoSelectionDto updateVideoSelectionById(Long selectionId, UpdateSelectionRequest request) {
        VideoSelection videoSelection = videoSelectionValidationHelper.isVideoSelectionPresent(selectionId);
        videoSelectionValidationHelper.isUpdateSelectionDateValid(videoSelection, request);
        videoSelectionMapper.updateVideoSelection(request, videoSelection);
        VideoSelection updatedVideoSelection = videoSelectionRepository.save(videoSelection);
        VideoSelectionDto videoSelectionDto = videoSelectionMapper
                .videoSelectionToVideoSelectionDto(updatedVideoSelection);
        log.info("Видео-отбор с ID {} обновлён.", selectionId);
        return videoSelectionDto;
    }

    @Override
    public List<VideoSelectionDto> getVideoSelectionsByGrandCompetitionId(Long competitionId, int from, int size) {
        grandCompetitionValidationHelper.isGrandCompetitionPresent(competitionId);
        int page = from / size;
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        Page<VideoSelection> selectionsPage = videoSelectionRepository.findByCompetition_Id(competitionId, pageRequest);

        if (selectionsPage.isEmpty()) {
            log.info("Не нашлось видео-отборов по заданным параметрам.");
            return new ArrayList<>();
        }

        List<VideoSelection> selections = selectionsPage.getContent();
        List<VideoSelectionDto> selectionDtos = videoSelectionMapper
                .videoSelectionListToVideoSelectionDtoList(selections);
        log.info("Список видео-отборов к оффлайн-соревнованию с ИД {} с номера {} размером {} возвращён.",
                competitionId, from, selectionDtos.size());
        return selectionDtos;
    }
}
