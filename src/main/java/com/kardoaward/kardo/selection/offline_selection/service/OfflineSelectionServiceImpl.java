package com.kardoaward.kardo.selection.offline_selection.service;

import com.kardoaward.kardo.grand_competition.model.GrandCompetition;
import com.kardoaward.kardo.grand_competition.service.helper.GrandCompetitionValidationHelper;
import com.kardoaward.kardo.selection.offline_selection.mapper.OfflineSelectionMapper;
import com.kardoaward.kardo.selection.offline_selection.model.OfflineSelection;
import com.kardoaward.kardo.selection.offline_selection.model.dto.NewOfflineSelectionRequest;
import com.kardoaward.kardo.selection.offline_selection.model.dto.OfflineSelectionDto;
import com.kardoaward.kardo.selection.offline_selection.repository.OfflineSelectionRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class OfflineSelectionServiceImpl implements OfflineSelectionService {

    private final OfflineSelectionRepository offlineSelectionRepository;

    private final OfflineSelectionMapper offlineSelectionMapper;

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
}
