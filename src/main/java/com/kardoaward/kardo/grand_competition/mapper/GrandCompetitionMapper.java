package com.kardoaward.kardo.grand_competition.mapper;

import com.kardoaward.kardo.grand_competition.model.GrandCompetition;
import com.kardoaward.kardo.grand_competition.dto.NewGrandCompetitionRequest;
import com.kardoaward.kardo.grand_competition.dto.GrandCompetitionDto;
import com.kardoaward.kardo.grand_competition.dto.UpdateGrandCompetitionRequest;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring")
public interface GrandCompetitionMapper {

    GrandCompetition newGrandCompetitionRequestToGrandCompetition(NewGrandCompetitionRequest newCompetition);

    GrandCompetitionDto grandCompetitionToGrandCompetitionDto(GrandCompetition returnedCompetition);

    List<GrandCompetitionDto> grandCompetitionListToGrandCompetitionDtoList(List<GrandCompetition> competitions);

    @BeanMapping(nullValuePropertyMappingStrategy = IGNORE)
    void updateGrandCompetition(UpdateGrandCompetitionRequest request, @MappingTarget GrandCompetition competition);
}
