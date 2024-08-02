package com.kardoaward.kardo.selection.service;

import com.kardoaward.kardo.grand_competition.model.GrandCompetition;
import com.kardoaward.kardo.grand_competition.service.helper.GrandCompetitionValidationHelper;
import com.kardoaward.kardo.selection.mapper.SelectionMapper;
import com.kardoaward.kardo.selection.model.Selection;
import com.kardoaward.kardo.selection.model.dto.NewSelectionRequest;
import com.kardoaward.kardo.selection.model.dto.SelectionDto;
import com.kardoaward.kardo.selection.model.dto.UpdateSelectionRequest;
import com.kardoaward.kardo.selection.repository.SelectionRepository;
import com.kardoaward.kardo.selection.service.helper.SelectionValidationHelper;
import com.kardoaward.kardo.user.service.helper.UserValidationHelper;
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
public class SelectionServiceImpl implements SelectionService {

    private final SelectionRepository selectionRepository;

    private final SelectionMapper selectionMapper;

    private final SelectionValidationHelper selectionValidationHelper;
    private final UserValidationHelper userValidationHelper;
    private final GrandCompetitionValidationHelper offlineValidationHelper;

    @Override
    @Transactional
    public SelectionDto addSelection(NewSelectionRequest newSelectionRequest) {
        GrandCompetition competition = offlineValidationHelper
                .isGrandCompetitionPresent(newSelectionRequest.getCompetitionId());
        Selection selection = selectionMapper.newSelectionRequestToSelection(newSelectionRequest, competition);
        Selection returnedSelection = selectionRepository.save(selection);
        SelectionDto selectionDto = selectionMapper.selectionToSelectionDto(returnedSelection);
        log.info("Отбор с ID = {} создан.", selectionDto.getId());
        return selectionDto;
    }

    @Override
    @Transactional
    public void deleteSelection(Long selectionId) {
        selectionValidationHelper.isSelectionPresent(selectionId);
        selectionRepository.deleteById(selectionId);
        log.info("Отбор с ID {} удалён.", selectionId);
    }

    @Override
    public SelectionDto getSelectionById(Long selectionId) {
        Selection selection = selectionValidationHelper.isSelectionPresent(selectionId);
        SelectionDto selectionDto = selectionMapper.selectionToSelectionDto(selection);
        log.info("Отбор с ИД {} возвращен.", selectionId);
        return selectionDto;
    }

    @Override
    public List<SelectionDto> getSelections(int from, int size) {
        int page = from / size;
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        Page<Selection> selectionsPage = selectionRepository.findAll(pageRequest);

        if (selectionsPage.isEmpty()) {
            log.info("Не нашлось отборов по заданным параметрам.");
            return new ArrayList<>();
        }

        List<Selection> selections = selectionsPage.getContent();
        List<SelectionDto> selectionDtos = selectionMapper.selectionListToSelectionDtoList(selections);
        log.info("Список отборов с номера {} размером {} возвращён.", from, selectionDtos.size());
        return selectionDtos;
    }

    @Override
    @Transactional
    public SelectionDto updateSelectionById(Long selectionId, UpdateSelectionRequest request) {
        Selection selection = selectionValidationHelper.isSelectionPresent(selectionId);
        selectionValidationHelper.isUpdateSelectionDateValid(selection, request);
        selectionMapper.updateSelection(request, selection);
        Selection updatedSelection = selectionRepository.save(selection);
        SelectionDto selectionDto = selectionMapper.selectionToSelectionDto(updatedSelection);
        log.info("Отбор с ID {} обновлён.", selectionId);
        return selectionDto;
    }

    @Override
    public List<SelectionDto> getSelectionsByRequestorId(Long requestorId, int from, int size) {
        userValidationHelper.isUserPresent(requestorId);
        int page = from / size;
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        Page<Selection> selectionsPage = selectionRepository.findAllByRequestorId(requestorId, pageRequest);

        if (selectionsPage.isEmpty()) {
            log.info("Не нашлось отборов по заданным параметрам.");
            return new ArrayList<>();
        }

        List<Selection> selections = selectionsPage.getContent();
        List<SelectionDto> selectionDtos = selectionMapper.selectionListToSelectionDtoList(selections);
        log.info("Список отборов с участием пользователя с ИД {} с номера {} размером {} возвращён.", requestorId,
                from, selectionDtos.size());
        return selectionDtos;
    }

    @Override
    public List<SelectionDto> getSelectionsByOfflineCompetitionId(Long competitionId, int from, int size) {
        offlineValidationHelper.isGrandCompetitionPresent(competitionId);
        int page = from / size;
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        Page<Selection> selectionsPage = selectionRepository.findByCompetition_Id(competitionId, pageRequest);

        if (selectionsPage.isEmpty()) {
            log.info("Не нашлось отборов по заданным параметрам.");
            return new ArrayList<>();
        }

        List<Selection> selections = selectionsPage.getContent();
        List<SelectionDto> selectionDtos = selectionMapper.selectionListToSelectionDtoList(selections);
        log.info("Список отборов к оффлайн-соревнованию с ИД {} с номера {} размером {} возвращён.", competitionId,
                from, selectionDtos.size());
        return selectionDtos;
    }
}
