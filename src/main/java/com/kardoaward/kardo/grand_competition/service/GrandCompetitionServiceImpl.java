package com.kardoaward.kardo.grand_competition.service;

import com.kardoaward.kardo.grand_competition.mapper.GrandCompetitionMapper;
import com.kardoaward.kardo.grand_competition.model.GrandCompetition;
import com.kardoaward.kardo.grand_competition.model.dto.UpdateGrandCompetitionRequest;
import com.kardoaward.kardo.grand_competition.repository.GrandCompetitionRepository;
import com.kardoaward.kardo.grand_competition.service.helper.GrandCompetitionValidationHelper;
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
public class GrandCompetitionServiceImpl implements GrandCompetitionService {

    private final GrandCompetitionRepository repository;

    private final GrandCompetitionMapper mapper;

    private final GrandCompetitionValidationHelper grandHelper;

    @Override
    @Transactional
    public GrandCompetition addGrandCompetition(GrandCompetition competition) {
        GrandCompetition returnedCompetition = repository.save(competition);
        log.info("Гранд-соревнование с ID = {} создано.", returnedCompetition.getId());
        return returnedCompetition;
    }

    @Override
    @Transactional
    public void deleteGrandCompetition(Long competitionId) {
        grandHelper.isGrandCompetitionPresent(competitionId);
        repository.deleteById(competitionId);
        log.info("Гранд-соревнование с ID {} удалено.", competitionId);
    }

    @Override
    public GrandCompetition getGrandCompetitionById(Long competitionId) {
        GrandCompetition competition = grandHelper.isGrandCompetitionPresent(competitionId);
        log.info("Гранд-соревнование с ИД {} возвращено.", competitionId);
        return competition;
    }

    @Override
    public List<GrandCompetition> getGrandCompetitions(int from, int size) {
        int page = from / size;
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        Page<GrandCompetition> competitionsPage = repository.findAll(pageRequest);

        if (competitionsPage.isEmpty()) {
            log.info("Не нашлось гранд-соревнований по заданным параметрам.");
            return new ArrayList<>();
        }

        List<GrandCompetition> competitions = competitionsPage.getContent();
        log.info("Список гранд-соревнований с номера {} размером {} возвращён.", from, competitions.size());
        return competitions;
    }

    @Override
    @Transactional
    public GrandCompetition updateGrandCompetition(Long competitionId, UpdateGrandCompetitionRequest request) {
        GrandCompetition competition = helper.isGrandCompetitionPresent(competitionId);
        mapper.updateGrandCompetition(request, competition);
        GrandCompetition updatedCompetition = repository.save(competition);
        log.info("Гранд-соревнование с ID {} обновлено.", competitionId);
        return updatedCompetition;
    }
}
