package com.kardoaward.kardo.offline_competition.service.helper;

import com.kardoaward.kardo.exception.NotFoundException;
import com.kardoaward.kardo.offline_competition.model.OfflineCompetition;
import com.kardoaward.kardo.offline_competition.repository.OfflineCompetitionRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class OfflineCompetitionValidationHelper {

    private final OfflineCompetitionRepository repository;

    public OfflineCompetition isOfflineCompetitionPresent(Long offlineCompetitionId) {
        Optional<OfflineCompetition> optionalOfflineCompetition = repository.findById(offlineCompetitionId);

        if (optionalOfflineCompetition.isEmpty()) {
            log.error("Оффлайн-соревнование с ИД {} отсутствует в БД.", offlineCompetitionId);
            throw new NotFoundException(String.format("Оффлайн-соревнование с ИД %d отсутствует в БД.",
                    offlineCompetitionId));
        }

        return optionalOfflineCompetition.get();
    }
}
