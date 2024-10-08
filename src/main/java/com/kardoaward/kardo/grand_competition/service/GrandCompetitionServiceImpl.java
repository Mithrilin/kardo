package com.kardoaward.kardo.grand_competition.service;

import com.kardoaward.kardo.grand_competition.mapper.GrandCompetitionMapper;
import com.kardoaward.kardo.grand_competition.model.GrandCompetition;
import com.kardoaward.kardo.grand_competition.dto.GrandCompetitionDto;
import com.kardoaward.kardo.grand_competition.dto.NewGrandCompetitionRequest;
import com.kardoaward.kardo.grand_competition.dto.UpdateGrandCompetitionRequest;
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
    public GrandCompetitionDto addGrandCompetition(NewGrandCompetitionRequest newCompetition) {
        grandHelper.isNewGrandCompetitionDateValid(newCompetition);
        GrandCompetition competition = mapper.newGrandCompetitionRequestToGrandCompetition(newCompetition);
        GrandCompetition returnedCompetition = repository.save(competition);
        GrandCompetitionDto grandCompetitionDto = mapper.grandCompetitionToGrandCompetitionDto(returnedCompetition);
        log.info("Гранд-соревнование с ID = {} создано.", grandCompetitionDto.getId());
        return grandCompetitionDto;
    }

    @Override
    @Transactional
    public void deleteGrandCompetition(Long competitionId) {
        grandHelper.isGrandCompetitionPresent(competitionId);
        repository.deleteById(competitionId);
        log.info("Гранд-соревнование с ID {} удалено.", competitionId);
    }

    @Override
    public GrandCompetitionDto getGrandCompetitionById(Long competitionId) {
        GrandCompetition competition = grandHelper.isGrandCompetitionPresent(competitionId);
        GrandCompetitionDto grandCompetitionDto = mapper.grandCompetitionToGrandCompetitionDto(competition);
        log.info("Гранд-соревнование с ИД {} возвращено.", competitionId);
        return grandCompetitionDto;
    }

    @Override
    public List<GrandCompetitionDto> getGrandCompetitions(int from, int size) {
        int page = from / size;
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        Page<GrandCompetition> competitionsPage = repository.findAll(pageRequest);

        if (competitionsPage.isEmpty()) {
            log.info("Не нашлось гранд-соревнований по заданным параметрам.");
            return new ArrayList<>();
        }

        List<GrandCompetition> competitions = competitionsPage.getContent();
        List<GrandCompetitionDto> grandCompetitionDtos = mapper
                .grandCompetitionListToGrandCompetitionDtoList(competitions);
        log.info("Список гранд-соревнований с номера {} размером {} возвращён.", from, grandCompetitionDtos.size());
        return grandCompetitionDtos;
    }

    @Override
    @Transactional
    public GrandCompetitionDto updateGrandCompetition(Long competitionId, UpdateGrandCompetitionRequest request) {
        GrandCompetition competition = grandHelper.isGrandCompetitionPresent(competitionId);
        grandHelper.isUpdateGrandCompetitionDateValid(competition, request);
        mapper.updateGrandCompetition(request, competition);
        GrandCompetition updatedCompetition = repository.save(competition);
        GrandCompetitionDto grandCompetitionDto = mapper.grandCompetitionToGrandCompetitionDto(updatedCompetition);
        log.info("Гранд-соревнование с ID {} обновлено.", competitionId);
        return grandCompetitionDto;
    }
}
