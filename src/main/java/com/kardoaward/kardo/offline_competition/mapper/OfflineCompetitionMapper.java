package com.kardoaward.kardo.offline_competition.mapper;

import com.kardoaward.kardo.offline_competition.model.OfflineCompetition;
import com.kardoaward.kardo.offline_competition.model.dto.NewOfflineCompetitionRequest;
import com.kardoaward.kardo.offline_competition.model.dto.OfflineCompetitionDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OfflineCompetitionMapper {

    OfflineCompetition newOfflineCompetitionRequestToOfflineCompetition(NewOfflineCompetitionRequest newCompetition);

    OfflineCompetitionDto offlineCompetitionToOfflineCompetitionDto(OfflineCompetition returnedCompetition);
}
