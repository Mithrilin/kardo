package com.kardoaward.kardo.spectator_request.selection_spectator_request.service;

import com.kardoaward.kardo.enums.RequestStatus;
import com.kardoaward.kardo.enums.UpdateRequestStatus;
import com.kardoaward.kardo.selection.offline_selection.model.OfflineSelection;
import com.kardoaward.kardo.selection.offline_selection.service.helper.OfflineSelectionValidationHelper;
import com.kardoaward.kardo.spectator_request.dto.update.SpectatorRequestStatusUpdateRequest;
import com.kardoaward.kardo.spectator_request.dto.update.SpectatorRequestStatusUpdateResult;
import com.kardoaward.kardo.spectator_request.selection_spectator_request.mapper.SelectionSpectatorRequestMapper;
import com.kardoaward.kardo.spectator_request.selection_spectator_request.model.SelectionSpectatorRequest;
import com.kardoaward.kardo.spectator_request.selection_spectator_request.dto.NewSelectionSpectatorRequest;
import com.kardoaward.kardo.spectator_request.selection_spectator_request.dto.SelectionSpectatorRequestDto;
import com.kardoaward.kardo.spectator_request.selection_spectator_request.repository.SelectionSpectatorRequestRepository;
import com.kardoaward.kardo.spectator_request.selection_spectator_request.service.helper.SelectionSpectatorRequestValidationHelper;
import com.kardoaward.kardo.user.model.User;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class SelectionSpectatorRequestServiceImpl implements SelectionSpectatorRequestService {

    private final SelectionSpectatorRequestRepository repository;

    private final SelectionSpectatorRequestMapper mapper;

    private final OfflineSelectionValidationHelper offlineSelectionValidationHelper;
    private final SelectionSpectatorRequestValidationHelper helper;

    @Override
    @Transactional
    public SelectionSpectatorRequestDto addSelectionSpectatorRequest(User requestor,
                                                                     NewSelectionSpectatorRequest request) {
        OfflineSelection offlineSelection = offlineSelectionValidationHelper
                .isOfflineSelectionPresent(request.getSelectionId());
        SelectionSpectatorRequest spectatorRequest = mapper
                .newSpectatorRequestToSpectatorRequest(offlineSelection, requestor);
        SelectionSpectatorRequest returnedSpectatorRequest = repository.save(spectatorRequest);
        SelectionSpectatorRequestDto spectatorRequestDto = mapper
                .spectatorRequestToSpectatorRequestDto(returnedSpectatorRequest);
        log.info("Заявка зрителя отбора с ИД {} пользователя с ИД {} создана.", spectatorRequestDto.getId(),
                requestor.getId());
        return spectatorRequestDto;
    }

    @Override
    @Transactional
    public void deleteSelectionSpectatorRequestById(User requestor, Long spectatorId) {
        SelectionSpectatorRequest spectatorRequest = helper.isSpectatorRequestPresent(spectatorId);
        helper.isUserRequesterOrAdmin(requestor, spectatorRequest.getRequester().getId());
        repository.deleteById(spectatorId);
        log.info("Заявка зрителя отбора с ИД {} пользователя с ИД {} удалена.", spectatorId, requestor.getId());
    }

    @Override
    public SelectionSpectatorRequestDto getSelectionSpectatorRequestById(User requestor, Long spectatorId) {
        SelectionSpectatorRequest spectatorRequest = helper.isSpectatorRequestPresent(spectatorId);
        helper.isUserRequesterOrAdmin(requestor, spectatorRequest.getRequester().getId());
        SelectionSpectatorRequestDto spectatorRequestDto = mapper
                .spectatorRequestToSpectatorRequestDto(spectatorRequest);
        log.info("Заявка зрителя отбора с ИД {} возвращена.", spectatorId);
        return spectatorRequestDto;
    }

    @Override
    public List<SelectionSpectatorRequestDto> getSelectionSpectatorRequestBySelectionId(Long selectionId, int from,
                                                                                        int size) {
        offlineSelectionValidationHelper.isOfflineSelectionPresent(selectionId);
        int page = from / size;
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        Page<SelectionSpectatorRequest> spectatorRequestsPage = repository.findBySelection_Id(selectionId, pageRequest);

        if (spectatorRequestsPage.isEmpty()) {
            log.info("Не нашлось заявок зрителей отборов по заданным параметрам.");
            return new ArrayList<>();
        }

        List<SelectionSpectatorRequest> spectatorRequests = spectatorRequestsPage.getContent();
        List<SelectionSpectatorRequestDto> spectatorRequestDtos = mapper
                .spectatorRequestListToSpectatorRequestDtoList(spectatorRequests);
        log.info("Список заявок зрителей к отбору с ИД {} с номера {} размером {} возвращён.", selectionId, from,
                spectatorRequestDtos.size());
        return spectatorRequestDtos;
    }

    @Override
    public SpectatorRequestStatusUpdateResult updateSelectionSpectatorRequestStatusBySelectionId(
            Long selectionId, SpectatorRequestStatusUpdateRequest request) {

        offlineSelectionValidationHelper.isOfflineSelectionPresent(selectionId);
        List<Long> ids = request.getRequestIds();
        List<SelectionSpectatorRequest> spectatorRequests = repository.findAllById(ids);
        SpectatorRequestStatusUpdateResult result = new SpectatorRequestStatusUpdateResult();
        List<SelectionSpectatorRequest> updatedRequests = new ArrayList<>();

        for (SelectionSpectatorRequest spectatorRequest : spectatorRequests) {
            SelectionSpectatorRequestDto spectatorRequestDto = mapper
                    .spectatorRequestToSpectatorRequestDto(spectatorRequest);

            if (spectatorRequest.getStatus() == RequestStatus.PENDING) {

                if (request.getStatus() == UpdateRequestStatus.CONFIRMED) {
                    spectatorRequest.setStatus(RequestStatus.CONFIRMED);
                } else {
                    spectatorRequest.setStatus(RequestStatus.CANCELED);
                }
                updatedRequests.add(spectatorRequest);
                result.getUpdatedRequests().add(spectatorRequestDto);
            } else {
                result.getNotUpdatedRequests().add(spectatorRequestDto);
            }
        }

        repository.saveAll(updatedRequests);
        log.info("Статуса заявок зрителей к отбору с ИД {} обновился у {} заявок и не обновился у {} заявок.",
                selectionId, result.getUpdatedRequests().size(), result.getNotUpdatedRequests().size());
        return result;
    }
}
