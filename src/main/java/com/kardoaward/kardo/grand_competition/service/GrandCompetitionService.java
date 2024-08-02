package com.kardoaward.kardo.grand_competition.service;

import com.kardoaward.kardo.grand_competition.model.GrandCompetition;
import com.kardoaward.kardo.grand_competition.model.dto.UpdateGrandCompetitionRequest;

import java.util.List;

public interface GrandCompetitionService {

    GrandCompetition addGrandCompetition(GrandCompetition competition);

    void deleteGrandCompetition(Long competitionId);

    GrandCompetition getGrandCompetitionById(Long competitionId);

    List<GrandCompetition> getGrandCompetitions(int from, int size);

    GrandCompetition updateGrandCompetition(Long competitionId, UpdateGrandCompetitionRequest request);
}
