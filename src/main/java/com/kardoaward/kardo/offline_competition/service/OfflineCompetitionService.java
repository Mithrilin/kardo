package com.kardoaward.kardo.offline_competition.service;

import com.kardoaward.kardo.offline_competition.model.OfflineCompetition;

public interface OfflineCompetitionService {

    OfflineCompetition addOfflineCompetition(OfflineCompetition competition);

    void deleteOfflineCompetition(Long competitionId);

    OfflineCompetition getOfflineCompetitionById(Long competitionId);
}
