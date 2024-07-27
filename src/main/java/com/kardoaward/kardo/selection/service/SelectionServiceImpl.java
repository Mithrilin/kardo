package com.kardoaward.kardo.selection.service;

import com.kardoaward.kardo.offline_competition.model.OfflineCompetition;
import com.kardoaward.kardo.selection.mapper.SelectionMapper;
import com.kardoaward.kardo.selection.model.Selection;
import com.kardoaward.kardo.selection.model.dto.NewSelectionRequest;
import com.kardoaward.kardo.selection.model.dto.SelectionDto;
import com.kardoaward.kardo.selection.repository.SelectionRepository;
import com.kardoaward.kardo.selection.service.helper.SelectionValidationHelper;
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

    @Override
    @Transactional
    public SelectionDto addSelection(NewSelectionRequest newSelectionRequest) {
        /* ToDo
            Исправить. Добавить хелпер.
         */
        OfflineCompetition competition = null;
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
            log.info("Не нашлось пользователей по заданным параметрам.");
            return new ArrayList<>();
        }

        List<Selection> selections = selectionsPage.getContent();
        List<SelectionDto> selectionDtos = selectionMapper.selectionListToSelectionDtoList(selections);
        log.info("Список пользователей с номера {} размером {} возвращён.", from, selectionDtos.size());
        return selectionDtos;
    }
}
