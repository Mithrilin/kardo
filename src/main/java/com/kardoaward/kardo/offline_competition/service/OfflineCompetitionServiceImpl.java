package com.kardoaward.kardo.offline_competition.service;

import com.kardoaward.kardo.offline_competition.model.OfflineCompetition;
import com.kardoaward.kardo.offline_competition.repository.OfflineCompetitionRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class OfflineCompetitionServiceImpl implements OfflineCompetitionService {

    private final OfflineCompetitionRepository repository;

    @Override
    public OfflineCompetition addOfflineCompetition(OfflineCompetition competition) {
        OfflineCompetition returnedCompetition = repository.save(competition);
        log.info("Оффлайн-соревнование с ID = {} создано.", returnedCompetition.getId());
        return returnedCompetition;
    }
}
