package com.kardoaward.kardo.participation_request.mapper;

import com.kardoaward.kardo.participation_request.model.ParticipationRequest;
import com.kardoaward.kardo.participation_request.model.dto.NewParticipationRequest;
import com.kardoaward.kardo.participation_request.model.dto.ParticipationRequestDto;
import com.kardoaward.kardo.selection.mapper.SelectionMapper;
import com.kardoaward.kardo.selection.model.Selection;
import com.kardoaward.kardo.user.mapper.UserMapper;
import com.kardoaward.kardo.user.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UserMapper.class, SelectionMapper.class})
public interface ParticipationRequestMapper {

    @Mapping(target = "id", constant = "0L")
    @Mapping(target = "status", constant = "PENDING")
    @Mapping(source = "selection", target = "selection")
    @Mapping(source = "user", target = "requester")
    ParticipationRequest newParticipationRequestToParticipationRequest(NewParticipationRequest newParticipationRequest,
                                                                       User user,
                                                                       Selection selection);

    @Mapping(source = "request.selection", target = "selectionDto")
    @Mapping(source = "request.requester", target = "requesterDto")
    ParticipationRequestDto participationRequestToParticipationRequestDto(ParticipationRequest request);
}
