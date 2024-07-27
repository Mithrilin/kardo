package com.kardoaward.kardo.selection.mapper;

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

@Mapper(componentModel = "spring")
public interface SelectionMapper {

    @Mapping(source = "competition", target = "competition")
    Selection newSelectionRequestToSelection(NewSelectionRequest newSelectionRequest, OfflineCompetition competition);

    SelectionDto selectionToSelectionDto(Selection returnedSelection);

    List<SelectionDto> selectionListToSelectionDtoList(List<Selection> selections);

    @BeanMapping(nullValuePropertyMappingStrategy = IGNORE)
    void updateSelection(UpdateSelectionRequest request, @MappingTarget Selection selection);
}
