package com.kardoaward.kardo.spectator_request.selection_spectator_request.mapper;

import com.kardoaward.kardo.selection.offline_selection.mapper.OfflineSelectionMapper;
import com.kardoaward.kardo.selection.offline_selection.model.OfflineSelection;
import com.kardoaward.kardo.spectator_request.selection_spectator_request.model.SelectionSpectatorRequest;
import com.kardoaward.kardo.spectator_request.selection_spectator_request.dto.SelectionSpectatorRequestDto;
import com.kardoaward.kardo.user.mapper.UserMapper;
import com.kardoaward.kardo.user.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {UserMapper.class, OfflineSelectionMapper.class})
public interface SelectionSpectatorRequestMapper {

    @Mapping(target = "id", constant = "0L")
    @Mapping(target = "status", constant = "PENDING")
    @Mapping(source = "user", target = "requester")
    @Mapping(source = "offlineSelection", target = "selection")
    SelectionSpectatorRequest newSpectatorRequestToSpectatorRequest(OfflineSelection offlineSelection, User user);

    @Mapping(source = "spectatorRequest.selection", target = "selectionDto")
    @Mapping(source = "spectatorRequest.requester", target = "requesterDto")
    SelectionSpectatorRequestDto spectatorRequestToSpectatorRequestDto(SelectionSpectatorRequest spectatorRequest);

    List<SelectionSpectatorRequestDto> spectatorRequestListToSpectatorRequestDtoList(List<SelectionSpectatorRequest> spectatorRequests);
}
