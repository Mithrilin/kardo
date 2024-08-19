package com.kardoaward.kardo.grand_competition.service;

import com.kardoaward.kardo.grand_competition.dto.GrandCompetitionDto;
import com.kardoaward.kardo.grand_competition.dto.NewGrandCompetitionRequest;
import com.kardoaward.kardo.grand_competition.dto.UpdateGrandCompetitionRequest;

import java.util.List;

public interface GrandCompetitionService {

    GrandCompetitionDto addGrandCompetition(NewGrandCompetitionRequest newCompetition);

    void deleteGrandCompetition(Long competitionId);

    GrandCompetitionDto getGrandCompetitionById(Long competitionId);

    List<GrandCompetitionDto> getGrandCompetitions(int from, int size);

    GrandCompetitionDto updateGrandCompetition(Long competitionId, UpdateGrandCompetitionRequest request);
}
