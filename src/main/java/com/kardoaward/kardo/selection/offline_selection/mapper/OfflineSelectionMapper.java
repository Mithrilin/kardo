package com.kardoaward.kardo.selection.offline_selection.mapper;

import com.kardoaward.kardo.grand_competition.mapper.GrandCompetitionMapper;
import com.kardoaward.kardo.grand_competition.model.GrandCompetition;
import com.kardoaward.kardo.selection.offline_selection.model.OfflineSelection;
import com.kardoaward.kardo.selection.offline_selection.model.dto.NewOfflineSelectionRequest;
import com.kardoaward.kardo.selection.offline_selection.model.dto.OfflineSelectionDto;
import com.kardoaward.kardo.selection.offline_selection.model.dto.UpdateOfflineSelectionRequest;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring", uses = GrandCompetitionMapper.class)
public interface OfflineSelectionMapper {

    @Mapping(target = "id", constant = "0L")
    @Mapping(target = "status", constant = "UPCOMING")
    @Mapping(source = "grandCompetition", target = "competition")
    @Mapping(source = "newOfflineSelectionRequest.title", target = "title")
    @Mapping(source = "newOfflineSelectionRequest.fields", target = "fields")
    @Mapping(source = "newOfflineSelectionRequest.location", target = "location")
    OfflineSelection newOfflineSelectionRequestToOfflineSelection(NewOfflineSelectionRequest newOfflineSelectionRequest,
                                                                  GrandCompetition grandCompetition);

    @Mapping(source = "returnedOfflineSelection.competition", target = "competitionDto")
    OfflineSelectionDto offlineSelectionToOfflineSelectionDto(OfflineSelection returnedOfflineSelection);

    List<OfflineSelectionDto> offlineSelectionListToOfflineSelectionDtoList(List<OfflineSelection> offlineSelections);

    @BeanMapping(nullValuePropertyMappingStrategy = IGNORE)
    void updateOfflineSelection(UpdateOfflineSelectionRequest request,
                                @MappingTarget OfflineSelection offlineSelection);
}
