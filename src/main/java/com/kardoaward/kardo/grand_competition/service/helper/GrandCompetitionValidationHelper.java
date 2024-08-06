package com.kardoaward.kardo.grand_competition.service.helper;

import com.kardoaward.kardo.exception.BadRequestException;
import com.kardoaward.kardo.exception.NotFoundException;
import com.kardoaward.kardo.grand_competition.model.GrandCompetition;
import com.kardoaward.kardo.grand_competition.model.dto.NewGrandCompetitionRequest;
import com.kardoaward.kardo.grand_competition.model.dto.UpdateGrandCompetitionRequest;
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

    public void isNewGrandCompetitionDateValid(NewGrandCompetitionRequest newCompetition) {
        if (newCompetition.getCompetitionEnd().isBefore(newCompetition.getCompetitionStart())) {
            log.error("Дата начала гранд-соревнования не может быть после его конца.");
            throw new BadRequestException("Дата начала гранд-соревнования не может быть после его конца.");
        }
    }

    public void isUpdateGrandCompetitionDateValid(GrandCompetition grandCompetition,
                                                  UpdateGrandCompetitionRequest request) {
        if (request.getCompetitionStart() != null
                && request.getCompetitionEnd() != null
                && request.getCompetitionEnd().isBefore(request.getCompetitionStart())) {
            log.error("Дата начала гранд-соревнования не может быть после его конца.");
            throw new BadRequestException("Дата начала гранд-соревнования не может быть после его конца.");
        }

        if (request.getCompetitionStart() != null
                && request.getCompetitionEnd() == null
                && request.getCompetitionStart().isAfter(grandCompetition.getCompetitionEnd())) {
            log.error("Дата начала гранд-соревнования не может быть после его конца.");
            throw new BadRequestException("Дата начала гранд-соревнования не может быть после его конца.");
        }

        if (request.getCompetitionStart() == null
                && request.getCompetitionEnd() != null
                && request.getCompetitionEnd().isBefore(grandCompetition.getCompetitionStart())) {
            log.error("Дата конца гранд-соревнования не может быть раньше его начала.");
            throw new BadRequestException("Дата конца гранд-соревнования не может быть раньше его начала.");
        }
    }
}
