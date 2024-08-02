package com.kardoaward.kardo.grand_competition.service.helper;

import com.kardoaward.kardo.exception.NotFoundException;
import com.kardoaward.kardo.grand_competition.model.GrandCompetition;
import com.kardoaward.kardo.grand_competition.repository.GrandCompetitionRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class GrandCompetitionValidationHelper {

    private final GrandCompetitionRepository repository;

    public GrandCompetition isGrandCompetitionPresent(Long grandCompetitionId) {
        Optional<GrandCompetition> optionalGrandCompetition = repository.findById(grandCompetitionId);

        if (optionalGrandCompetition.isEmpty()) {
            log.error("Гранд-соревнование с ИД {} отсутствует в БД.", grandCompetitionId);
            throw new NotFoundException(String.format("Гранд-соревнование с ИД %d отсутствует в БД.",
                    grandCompetitionId));
        }

        return optionalGrandCompetition.get();
    }
}
