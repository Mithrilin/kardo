package com.kardoaward.kardo.online_competition.service;

import com.kardoaward.kardo.online_competition.model.dto.NewOnlineCompetitionRequest;
import com.kardoaward.kardo.online_competition.model.dto.OnlineCompetitionDto;
import com.kardoaward.kardo.online_competition.model.dto.UpdateOnlineCompetitionRequest;

import java.util.List;

public interface OnlineCompetitionService {

    OnlineCompetitionDto createOnlineCompetition(NewOnlineCompetitionRequest newCompetition);

    void deleteOnlineCompetition(Long competitionId);



    OnlineCompetitionDto updateOnlineCompetition(Long competitionId, UpdateOnlineCompetitionRequest request);
}