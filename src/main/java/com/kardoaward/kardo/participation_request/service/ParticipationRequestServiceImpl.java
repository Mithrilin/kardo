package com.kardoaward.kardo.participation_request.service;

import com.kardoaward.kardo.participation_request.mapper.ParticipationRequestMapper;
import com.kardoaward.kardo.participation_request.model.ParticipationRequest;
import com.kardoaward.kardo.participation_request.model.dto.NewParticipationRequest;
import com.kardoaward.kardo.participation_request.model.dto.ParticipationRequestDto;
import com.kardoaward.kardo.participation_request.repository.ParticipationRequestRepository;
import com.kardoaward.kardo.participation_request.service.helper.ParticipationRequestValidationHelper;
import com.kardoaward.kardo.selection.model.Selection;
import com.kardoaward.kardo.selection.service.helper.SelectionValidationHelper;
import com.kardoaward.kardo.user.model.User;
import com.kardoaward.kardo.user.service.helper.UserValidationHelper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class ParticipationRequestServiceImpl implements ParticipationRequestService {

    private final ParticipationRequestRepository repository;

    private final ParticipationRequestMapper mapper;

    private final ParticipationRequestValidationHelper helper;
    private final UserValidationHelper userValidationHelper;
    private final SelectionValidationHelper selectionValidationHelper;

    @Override
    @Transactional
    public ParticipationRequestDto addParticipation(Long requestorId, NewParticipationRequest newParticipationRequest) {
        User user = userValidationHelper.isUserPresent(requestorId);
        Selection selection = selectionValidationHelper.isSelectionPresent(newParticipationRequest.getSelectionId());
        ParticipationRequest request = mapper.newParticipationRequestToParticipationRequest(newParticipationRequest,
                user, selection);
        ParticipationRequest returnedRequest = repository.save(request);
        ParticipationRequestDto requestDto = mapper.participationRequestToParticipationRequestDto(returnedRequest);
        log.info("Заявка с ИД {} пользователя с ИД {} на участие в отборе с ИД {} создана.", requestDto.getId(),
                requestorId, selection.getId());
        return null;
    }

    @Override
    @Transactional
    public void deleteParticipationById(Long requestorId, Long participationId) {
        User user = userValidationHelper.isUserPresent(requestorId);
        ParticipationRequest request = helper.isParticipationRequestPresent(participationId);
        helper.isUserRequester(user, request);
        repository.deleteById(participationId);
        log.info("Заявка с ИД {} пользователя с ИД {} удалена.", participationId, requestorId);
    }
}
