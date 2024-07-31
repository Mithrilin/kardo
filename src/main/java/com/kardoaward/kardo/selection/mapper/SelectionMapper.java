package com.kardoaward.kardo.selection.mapper;

import com.kardoaward.kardo.offline_competition.mapper.OfflineCompetitionMapper;
import com.kardoaward.kardo.offline_competition.model.OfflineCompetition;
import com.kardoaward.kardo.selection.model.Selection;
import com.kardoaward.kardo.selection.model.dto.NewSelectionRequest;
import com.kardoaward.kardo.selection.model.dto.SelectionDto;
import com.kardoaward.kardo.selection.model.dto.UpdateSelectionRequest;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring", uses = OfflineCompetitionMapper.class)
public interface SelectionMapper {

    @Mapping(target = "id", constant = "0L")
    @Mapping(target = "status", constant = "UPCOMING")
    @Mapping(source = "offlineCompetition", target = "competition")
    @Mapping(source = "newSelectionRequest.title", target = "title")
    @Mapping(source = "newSelectionRequest.hashtag", target = "hashtag")
    @Mapping(source = "newSelectionRequest.fields", target = "fields")
    @Mapping(source = "newSelectionRequest.location", target = "location")
    Selection newSelectionRequestToSelection(NewSelectionRequest newSelectionRequest, OfflineCompetition offlineCompetition);

    @Mapping(source = "returnedSelection.competition", target = "competitionDto")
    SelectionDto selectionToSelectionDto(Selection returnedSelection);

    List<SelectionDto> selectionListToSelectionDtoList(List<Selection> selections);

    @BeanMapping(nullValuePropertyMappingStrategy = IGNORE)
    void updateSelection(UpdateSelectionRequest request, @MappingTarget Selection selection);
}
