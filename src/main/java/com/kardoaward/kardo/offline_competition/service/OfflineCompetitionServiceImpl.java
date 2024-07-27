package com.kardoaward.kardo.offline_competition.service;

import com.kardoaward.kardo.offline_competition.model.OfflineCompetition;
import com.kardoaward.kardo.offline_competition.repository.OfflineCompetitionRepository;
import com.kardoaward.kardo.offline_competition.service.helper.OfflineCompetitionValidationHelper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class OfflineCompetitionServiceImpl implements OfflineCompetitionService {

    private final OfflineCompetitionRepository repository;

    private final OfflineCompetitionValidationHelper helper;

    @Override
    @Transactional
    public OfflineCompetition addOfflineCompetition(OfflineCompetition competition) {
        OfflineCompetition returnedCompetition = repository.save(competition);
        log.info("Оффлайн-соревнование с ID = {} создано.", returnedCompetition.getId());
        return returnedCompetition;
    }

    @Override
    @Transactional
    public void deleteOfflineCompetition(Long competitionId) {
        helper.isOfflineCompetitionPresent(competitionId);
        repository.deleteById(competitionId);
        log.info("Оффлайн-соревнование с ID {} удалено.", competitionId);
    }
}
