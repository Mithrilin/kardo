package com.kardoaward.kardo.offline_competition.mapper;

import com.kardoaward.kardo.offline_competition.model.OfflineCompetition;
import com.kardoaward.kardo.offline_competition.model.dto.NewOfflineCompetitionRequest;
import com.kardoaward.kardo.offline_competition.model.dto.OfflineCompetitionDto;
import com.kardoaward.kardo.offline_competition.model.dto.UpdateOfflineCompetitionRequest;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring")
public interface OfflineCompetitionMapper {

    OfflineCompetition newOfflineCompetitionRequestToOfflineCompetition(NewOfflineCompetitionRequest newCompetition);

    OfflineCompetitionDto offlineCompetitionToOfflineCompetitionDto(OfflineCompetition returnedCompetition);

    List<OfflineCompetitionDto> offlineCompetitionListToOfflineCompetitionDtoList(List<OfflineCompetition> competitions);

    @BeanMapping(nullValuePropertyMappingStrategy = IGNORE)
    void updateOfflineCompetition(UpdateOfflineCompetitionRequest request, @MappingTarget OfflineCompetition competition);
}
