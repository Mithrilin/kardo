package com.kardoaward.kardo.online_competition.mapper;

import com.kardoaward.kardo.online_competition.model.OnlineCompetition;
import com.kardoaward.kardo.online_competition.model.dto.NewOnlineCompetitionRequest;
import com.kardoaward.kardo.online_competition.model.dto.OnlineCompetitionDto;
import com.kardoaward.kardo.online_competition.model.dto.UpdateOnlineCompetitionRequest;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring")
public interface OnlineCompetitionMapper {

    OnlineCompetition newOnlineCompetitionRequestToOnlineCompetition(NewOnlineCompetitionRequest newCompetition);

    OnlineCompetitionDto onlineCompetitionToOnlineCompetitionDto(OnlineCompetition competition);

    List<OnlineCompetitionDto> onlineCompetitionListToOnlineCompetitionDtoList(List<OnlineCompetition> competitions);






}
