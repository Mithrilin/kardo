package com.kardoaward.kardo.offline_competition.service;

import com.kardoaward.kardo.offline_competition.model.OfflineCompetition;
import com.kardoaward.kardo.offline_competition.model.dto.UpdateOfflineCompetitionRequest;

import java.util.List;

public interface OfflineCompetitionService {

    OfflineCompetition addOfflineCompetition(OfflineCompetition competition);

    void deleteOfflineCompetition(Long competitionId);

    OfflineCompetition getOfflineCompetitionById(Long competitionId);

    List<OfflineCompetition> getOfflineCompetitions(int from, int size);

    OfflineCompetition updateOfflineCompetition(Long competitionId, UpdateOfflineCompetitionRequest request);
}
