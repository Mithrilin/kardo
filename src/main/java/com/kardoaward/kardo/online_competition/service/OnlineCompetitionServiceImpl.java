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

    @Override
    public OnlineCompetitionDto getOnlineCompetitionById(Long competitionId) {
        OnlineCompetition competition = helper.isOnlineCompetitionPresent(competitionId);
        OnlineCompetitionDto competitionDto = mapper.onlineCompetitionToOnlineCompetitionDto(competition);
        log.info("Онлайн-соревнование с ИД {} возвращено.", competitionId);
        return competitionDto;
    }

    @Override
    public List<OnlineCompetitionDto> getOnlineCompetitions(int from, int size) {
        int page = from / size;
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        Page<OnlineCompetition> competitionsPage = repository.findAll(pageRequest);

        if (competitionsPage.isEmpty()) {
            log.info("Не нашлось онлайн-соревнований по заданным параметрам.");
            return new ArrayList<>();
        }

        List<OnlineCompetition> competitions = competitionsPage.getContent();
        List<OnlineCompetitionDto> competitionDtos = mapper.onlineCompetitionListToOnlineCompetitionDtoList(competitions);
        log.info("Список онлайн-соревнований с номера {} размером {} возвращён.", from, competitionDtos.size());
        return competitionDtos;
    }

    @Override
    @Transactional
    public OnlineCompetitionDto updateOnlineCompetition(Long competitionId, UpdateOnlineCompetitionRequest request) {
        OnlineCompetition competition = helper.isOnlineCompetitionPresent(competitionId);
        mapper.updateOnlineCompetition(request, competition);
        OnlineCompetition updatedCompetition = repository.save(competition);
        OnlineCompetitionDto competitionDto = mapper.onlineCompetitionToOnlineCompetitionDto(updatedCompetition);
        log.info("Онлайн-соревнование с ID {} обновлено.", competitionId);
        return competitionDto;
    }
}