package com.kardoaward.kardo.offline_competition.service;

import com.kardoaward.kardo.offline_competition.mapper.OfflineCompetitionMapper;
import com.kardoaward.kardo.offline_competition.model.OfflineCompetition;
import com.kardoaward.kardo.offline_competition.model.dto.UpdateOfflineCompetitionRequest;
import com.kardoaward.kardo.offline_competition.repository.OfflineCompetitionRepository;
import com.kardoaward.kardo.offline_competition.service.helper.OfflineCompetitionValidationHelper;
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
public class OfflineCompetitionServiceImpl implements OfflineCompetitionService {

    private final OfflineCompetitionRepository repository;

    private final OfflineCompetitionMapper mapper;

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

    @Override
    public OfflineCompetition getOfflineCompetitionById(Long competitionId) {
        OfflineCompetition competition = helper.isOfflineCompetitionPresent(competitionId);
        log.info("Оффлайн-соревнование с ИД {} возвращено.", competitionId);
        return competition;
    }

    @Override
    public List<OfflineCompetition> getOfflineCompetitions(int from, int size) {
        int page = from / size;
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        Page<OfflineCompetition> competitionsPage = repository.findAll(pageRequest);

        if (competitionsPage.isEmpty()) {
            log.info("Не нашлось оффлайн-соревнований по заданным параметрам.");
            return new ArrayList<>();
        }

        List<OfflineCompetition> competitions = competitionsPage.getContent();
        log.info("Список оффлайн-соревнований с номера {} размером {} возвращён.", from, competitions.size());
        return competitions;
    }

    @Override
    @Transactional
    public OfflineCompetition updateOfflineCompetition(Long competitionId, UpdateOfflineCompetitionRequest request) {
        OfflineCompetition competition = helper.isOfflineCompetitionPresent(competitionId);
        mapper.updateOfflineCompetition(request, competition);
        OfflineCompetition updatedCompetition = repository.save(competition);
        log.info("Оффлайн-соревнование с ID {} обновлено.", competitionId);
        return updatedCompetition;
    }
}
