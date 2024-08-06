package com.kardoaward.kardo.spectator_request.selection_spectator_request.service;

import com.kardoaward.kardo.selection.offline_selection.model.OfflineSelection;
import com.kardoaward.kardo.selection.offline_selection.service.helper.OfflineSelectionValidationHelper;
import com.kardoaward.kardo.spectator_request.selection_spectator_request.mapper.SelectionSpectatorRequestMapper;
import com.kardoaward.kardo.spectator_request.selection_spectator_request.model.SelectionSpectatorRequest;
import com.kardoaward.kardo.spectator_request.selection_spectator_request.model.dto.NewSelectionSpectatorRequest;
import com.kardoaward.kardo.spectator_request.selection_spectator_request.model.dto.SelectionSpectatorRequestDto;
import com.kardoaward.kardo.spectator_request.selection_spectator_request.repository.SelectionSpectatorRequestRepository;
import com.kardoaward.kardo.spectator_request.selection_spectator_request.service.helper.SelectionSpectatorRequestValidationHelper;
import com.kardoaward.kardo.user.model.User;
import com.kardoaward.kardo.user.service.helper.UserValidationHelper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class SelectionSpectatorRequestServiceImpl implements SelectionSpectatorRequestService {

    private final SelectionSpectatorRequestRepository repository;

    private final SelectionSpectatorRequestMapper mapper;

    private final UserValidationHelper userValidationHelper;
    private final OfflineSelectionValidationHelper offlineSelectionValidationHelper;
    private final SelectionSpectatorRequestValidationHelper helper;

    @Override
    @Transactional
    public SelectionSpectatorRequestDto addSelectionSpectatorRequest(Long requestorId,
                                                                     NewSelectionSpectatorRequest request) {
        User user = userValidationHelper.isUserPresent(requestorId);
        OfflineSelection offlineSelection = offlineSelectionValidationHelper
                .isOfflineSelectionPresent(request.getSelectionId());
        SelectionSpectatorRequest spectatorRequest = mapper
                .newSpectatorRequestToSpectatorRequest(offlineSelection, user);
        SelectionSpectatorRequest returnedSpectatorRequest = repository.save(spectatorRequest);
        SelectionSpectatorRequestDto spectatorRequestDto = mapper
                .spectatorRequestToSpectatorRequestDto(returnedSpectatorRequest);
        log.info("Заявка зрителя с ИД {} пользователя с ИД {} создана.", spectatorRequestDto, requestorId);
        return spectatorRequestDto;
    }

    @Override
    @Transactional
    public void deleteSelectionSpectatorRequestById(Long requestorId, Long spectatorId) {
        userValidationHelper.isUserPresent(requestorId);
        SelectionSpectatorRequest spectatorRequest = helper.isSpectatorRequestPresent(spectatorId);
        helper.isUserRequester(requestorId, spectatorRequest.getRequester().getId());
        repository.deleteById(spectatorId);
        log.info("Заявка зрителя с ИД {} пользователя с ИД {} удалена.", spectatorId, requestorId);
    }

    @Override
    public SelectionSpectatorRequestDto getSelectionSpectatorRequestById(Long requestorId, Long spectatorId) {
        userValidationHelper.isUserPresent(requestorId);
        SelectionSpectatorRequest spectatorRequest = helper.isSpectatorRequestPresent(spectatorId);
        helper.isUserRequester(requestorId, spectatorRequest.getRequester().getId());
        SelectionSpectatorRequestDto spectatorRequestDto = mapper
                .spectatorRequestToSpectatorRequestDto(spectatorRequest);
        log.info("Заявка зрителя с ИД {} пользователя с ИД {} возвращена.", spectatorId, requestorId);
        return spectatorRequestDto;
    }
}
