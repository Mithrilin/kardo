package com.kardoaward.kardo.selection.video_selection.mapper;

import com.kardoaward.kardo.grand_competition.mapper.GrandCompetitionMapper;
import com.kardoaward.kardo.grand_competition.model.GrandCompetition;
import com.kardoaward.kardo.selection.model.dto.UpdateSelectionRequest;
import com.kardoaward.kardo.selection.video_selection.model.VideoSelection;
import com.kardoaward.kardo.selection.video_selection.model.dto.NewVideoSelectionRequest;
import com.kardoaward.kardo.selection.video_selection.model.dto.VideoSelectionDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring", uses = GrandCompetitionMapper.class)
public interface VideoSelectionMapper {

    @Mapping(target = "id", constant = "0L")
    @Mapping(target = "status", constant = "UPCOMING")
    @Mapping(source = "grandCompetition", target = "competition")
    @Mapping(source = "newVideoSelectionRequest.title", target = "title")
    @Mapping(source = "newVideoSelectionRequest.hashtag", target = "hashtag")
    VideoSelection newVideoSelectionRequestToVideoSelection(NewVideoSelectionRequest newVideoSelectionRequest,
                                                            GrandCompetition grandCompetition);

    @Mapping(source = "returnedVideoSelection.competition", target = "competitionDto")
    VideoSelectionDto videoSelectionToVideoSelectionDto(VideoSelection returnedVideoSelection);

    List<VideoSelectionDto> videoSelectionListToVideoSelectionDtoList(List<VideoSelection> selections);

    @BeanMapping(nullValuePropertyMappingStrategy = IGNORE)
    void updateVideoSelection(UpdateSelectionRequest request, @MappingTarget VideoSelection selection);
}
