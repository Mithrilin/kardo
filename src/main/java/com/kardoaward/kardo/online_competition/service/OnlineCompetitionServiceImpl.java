package com.kardoaward.kardo.online_competition.service;

import com.kardoaward.kardo.online_competition.mapper.OnlineCompetitionMapper;
import com.kardoaward.kardo.online_competition.model.OnlineCompetition;
import com.kardoaward.kardo.online_competition.model.dto.NewOnlineCompetitionRequest;
import com.kardoaward.kardo.online_competition.model.dto.OnlineCompetitionDto;
import com.kardoaward.kardo.online_competition.model.dto.UpdateOnlineCompetitionRequest;
import com.kardoaward.kardo.online_competition.repository.OnlineCompetitionRepository;
import com.kardoaward.kardo.online_competition.service.helper.OnlineCompetitionValidationHelper;
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
public class OnlineCompetitionServiceImpl implements OnlineCompetitionService {

    private final OnlineCompetitionRepository repository;

    private final OnlineCompetitionMapper mapper;

    private final OnlineCompetitionValidationHelper helper;

    @Override
    @Transactional
    public OnlineCompetitionDto createOnlineCompetition(NewOnlineCompetitionRequest newCompetition) {
        OnlineCompetition competition = mapper.newOnlineCompetitionRequestToOnlineCompetition(newCompetition);
        OnlineCompetition returnedCompetition = repository.save(competition);
        OnlineCompetitionDto competitionDto = mapper.onlineCompetitionToOnlineCompetitionDto(returnedCompetition);
        log.info("Онлайн-соревнование с ID = {} создано.", returnedCompetition.getId());
        return competitionDto;
    }

    @Override
    @Transactional
    public void deleteOnlineCompetition(Long competitionId) {
        helper.isOnlineCompetitionPresent(competitionId);
        repository.deleteById(competitionId);
        log.info("Онлайн-соревнование с ID {} удалено.", competitionId);
    }






}
