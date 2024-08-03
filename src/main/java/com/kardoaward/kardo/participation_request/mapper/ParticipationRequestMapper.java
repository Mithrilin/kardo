package com.kardoaward.kardo.participation_request.mapper;

import com.kardoaward.kardo.participation_request.model.ParticipationRequest;
import com.kardoaward.kardo.participation_request.model.dto.NewParticipationRequest;
import com.kardoaward.kardo.participation_request.model.dto.ParticipationRequestDto;
import com.kardoaward.kardo.participation_request.model.dto.update.UpdateParticipationRequest;
import com.kardoaward.kardo.selection.offline_selection.mapper.OfflineSelectionMapper;
import com.kardoaward.kardo.selection.offline_selection.model.OfflineSelection;
import com.kardoaward.kardo.user.mapper.UserMapper;
import com.kardoaward.kardo.user.model.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring", uses = {UserMapper.class, OfflineSelectionMapper.class})
public interface ParticipationRequestMapper {

    @Mapping(target = "id", constant = "0L")
    @Mapping(target = "status", constant = "PENDING")
    @Mapping(source = "selection", target = "selection")
    @Mapping(source = "user", target = "requester")
    @Mapping(source = "newParticipationRequest.fields", target = "fields")
    ParticipationRequest newParticipationRequestToParticipationRequest(NewParticipationRequest newParticipationRequest,
                                                                       User user,
                                                                       OfflineSelection selection);

    @Mapping(source = "request.selection", target = "selectionDto")
    @Mapping(source = "request.requester", target = "requesterDto")
    ParticipationRequestDto participationRequestToParticipationRequestDto(ParticipationRequest request);

    List<ParticipationRequestDto> participationRequestListToParticipationRequestDtoList(List<ParticipationRequest> participations);

    @BeanMapping(nullValuePropertyMappingStrategy = IGNORE)
    void updateParticipation(UpdateParticipationRequest updateRequest, @MappingTarget ParticipationRequest request);
}
