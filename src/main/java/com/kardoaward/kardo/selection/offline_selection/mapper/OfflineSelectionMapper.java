package com.kardoaward.kardo.selection.offline_selection.mapper;

import com.kardoaward.kardo.grand_competition.mapper.GrandCompetitionMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = GrandCompetitionMapper.class)
public interface OfflineSelectionMapper {
}
