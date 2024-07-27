package com.kardoaward.kardo.offline_competition.service;

import com.kardoaward.kardo.offline_competition.model.OfflineCompetition;

import java.util.List;

public interface OfflineCompetitionService {

    OfflineCompetition addOfflineCompetition(OfflineCompetition competition);

    void deleteOfflineCompetition(Long competitionId);

    OfflineCompetition getOfflineCompetitionById(Long competitionId);

    List<OfflineCompetition> getOfflineCompetitions(int from, int size);
}
