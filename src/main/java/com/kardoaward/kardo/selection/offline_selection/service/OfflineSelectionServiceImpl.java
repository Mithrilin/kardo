package com.kardoaward.kardo.selection.offline_selection.service;

import com.kardoaward.kardo.grand_competition.model.GrandCompetition;
import com.kardoaward.kardo.grand_competition.service.helper.GrandCompetitionValidationHelper;
import com.kardoaward.kardo.selection.offline_selection.mapper.OfflineSelectionMapper;
import com.kardoaward.kardo.selection.offline_selection.model.OfflineSelection;
import com.kardoaward.kardo.selection.offline_selection.model.dto.NewOfflineSelectionRequest;
import com.kardoaward.kardo.selection.offline_selection.model.dto.OfflineSelectionDto;
import com.kardoaward.kardo.selection.offline_selection.model.dto.UpdateOfflineSelectionRequest;
import com.kardoaward.kardo.selection.offline_selection.repository.OfflineSelectionRepository;
import com.kardoaward.kardo.selection.offline_selection.service.helper.OfflineSelectionValidationHelper;
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
public class OfflineSelectionServiceImpl implements OfflineSelectionService {

    private final OfflineSelectionRepository offlineSelectionRepository;

    private final OfflineSelectionMapper offlineSelectionMapper;

    private final OfflineSelectionValidationHelper offlineSelectionValidationHelper;
    private final GrandCompetitionValidationHelper grandCompetitionValidationHelper;

    @Override
    @Transactional
    public OfflineSelectionDto addOfflineSelection(NewOfflineSelectionRequest newOfflineSelectionRequest) {
        GrandCompetition competition = grandCompetitionValidationHelper
                .isGrandCompetitionPresent(newOfflineSelectionRequest.getCompetitionId());
        OfflineSelection offlineSelection = offlineSelectionMapper
                .newOfflineSelectionRequestToOfflineSelection(newOfflineSelectionRequest, competition);
        OfflineSelection returnedOfflineSelection = offlineSelectionRepository.save(offlineSelection);
        OfflineSelectionDto offlineSelectionDto = offlineSelectionMapper
                .offlineSelectionToOfflineSelectionDto(returnedOfflineSelection);
        log.info("Оффлайн-отбор с ID = {} создан.", offlineSelectionDto.getId());
        return offlineSelectionDto;
    }

    @Override
    @Transactional
    public void deleteOfflineSelection(Long selectionId) {
        offlineSelectionValidationHelper.isOfflineSelectionPresent(selectionId);
        offlineSelectionRepository.deleteById(selectionId);
        log.info("Оффлайн-отбор с ID {} удалён.", selectionId);
    }

    @Override
    public OfflineSelectionDto getOfflineSelectionById(Long selectionId) {
        OfflineSelection offlineSelection = offlineSelectionValidationHelper.isOfflineSelectionPresent(selectionId);
        OfflineSelectionDto offlineSelectionDto = offlineSelectionMapper
                .offlineSelectionToOfflineSelectionDto(offlineSelection);
        log.info("Оффлайн-отбор с ИД {} возвращен.", selectionId);
        return offlineSelectionDto;
    }

    @Override
    public List<OfflineSelectionDto> getOfflineSelections(int from, int size) {
        int page = from / size;
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        Page<OfflineSelection> selectionsPage = offlineSelectionRepository.findAll(pageRequest);

        if (selectionsPage.isEmpty()) {
            log.info("Не нашлось оффлайн-отборов по заданным параметрам.");
            return new ArrayList<>();
        }

        List<OfflineSelection> offlineSelections = selectionsPage.getContent();
        List<OfflineSelectionDto> offlineSelectionDtos = offlineSelectionMapper
                .offlineSelectionListToOfflineSelectionDtoList(offlineSelections);
        log.info("Список оффлайн-отборов с номера {} размером {} возвращён.", from, offlineSelectionDtos.size());
        return offlineSelectionDtos;
    }

    @Override
    @Transactional
    public OfflineSelectionDto updateOfflineSelectionById(Long selectionId, UpdateOfflineSelectionRequest request) {
        OfflineSelection offlineSelection = offlineSelectionValidationHelper.isOfflineSelectionPresent(selectionId);
        offlineSelectionValidationHelper.isUpdateOfflineSelectionDateValid(offlineSelection, request);
        offlineSelectionMapper.updateOfflineSelection(request, offlineSelection);
        OfflineSelection updatedOfflineSelection = offlineSelectionRepository.save(offlineSelection);
        OfflineSelectionDto offlineSelectionDto = offlineSelectionMapper
                .offlineSelectionToOfflineSelectionDto(updatedOfflineSelection);
        log.info("Оффлайн-отбор с ID {} обновлён.", selectionId);
        return offlineSelectionDto;
    }
}
