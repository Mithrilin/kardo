package com.kardoaward.kardo.selection.offline_selection.mapper;

import com.kardoaward.kardo.grand_competition.mapper.GrandCompetitionMapper;
import com.kardoaward.kardo.grand_competition.model.GrandCompetition;
import com.kardoaward.kardo.selection.offline_selection.model.OfflineSelection;
import com.kardoaward.kardo.selection.offline_selection.model.dto.NewOfflineSelectionRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

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
}
