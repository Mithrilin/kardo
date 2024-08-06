package com.kardoaward.kardo.participation_request.service;

import com.kardoaward.kardo.enums.RequestStatus;
import com.kardoaward.kardo.enums.UpdateRequestStatus;
import com.kardoaward.kardo.participation_request.mapper.ParticipationRequestMapper;
import com.kardoaward.kardo.participation_request.model.ParticipationRequest;
import com.kardoaward.kardo.participation_request.model.dto.NewParticipationRequest;
import com.kardoaward.kardo.participation_request.model.dto.ParticipationRequestDto;
import com.kardoaward.kardo.participation_request.model.dto.update.ParticipationRequestStatusUpdateRequest;
import com.kardoaward.kardo.participation_request.model.dto.update.ParticipationRequestStatusUpdateResult;
import com.kardoaward.kardo.participation_request.model.dto.update.UpdateParticipationRequest;
import com.kardoaward.kardo.participation_request.repository.ParticipationRequestRepository;
import com.kardoaward.kardo.participation_request.service.helper.ParticipationRequestValidationHelper;
import com.kardoaward.kardo.selection.offline_selection.model.OfflineSelection;
import com.kardoaward.kardo.selection.offline_selection.service.helper.OfflineSelectionValidationHelper;
import com.kardoaward.kardo.user.model.User;
import com.kardoaward.kardo.user.service.helper.UserValidationHelper;
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
public class ParticipationRequestServiceImpl implements ParticipationRequestService {

    private final ParticipationRequestRepository repository;

    private final ParticipationRequestMapper mapper;

    private final ParticipationRequestValidationHelper participationHelper;
    private final UserValidationHelper userValidationHelper;
    private final OfflineSelectionValidationHelper offlineSelectionValidationHelper;

    @Override
    @Transactional
    public ParticipationRequestDto addParticipation(Long requestorId, NewParticipationRequest newParticipationRequest) {
        User user = userValidationHelper.isUserPresent(requestorId);
        OfflineSelection offlineSelection = offlineSelectionValidationHelper
                .isOfflineSelectionPresent(newParticipationRequest.getSelectionId());
        ParticipationRequest request = mapper.newParticipationRequestToParticipationRequest(newParticipationRequest,
                user, offlineSelection);
        ParticipationRequest returnedRequest = repository.save(request);
        ParticipationRequestDto requestDto = mapper.participationRequestToParticipationRequestDto(returnedRequest);
        log.info("Заявка с ИД {} пользователя с ИД {} на участие в отборе с ИД {} создана.", requestDto.getId(),
                requestorId, offlineSelection.getId());
        return requestDto;
    }

    @Override
    @Transactional
    public void deleteParticipationById(Long requestorId, Long participationId) {
        userValidationHelper.isUserPresent(requestorId);
        ParticipationRequest request = participationHelper.isParticipationRequestPresent(participationId);
        participationHelper.isUserRequester(requestorId, request.getRequester().getId());
        repository.deleteById(participationId);
        log.info("Заявка с ИД {} пользователя с ИД {} удалена.", participationId, requestorId);
    }

    @Override
    public ParticipationRequestDto getParticipationById(Long requestorId, Long participationId) {
        userValidationHelper.isUserPresent(requestorId);
        ParticipationRequest request = participationHelper.isParticipationRequestPresent(participationId);
        participationHelper.isUserRequester(requestorId, request.getRequester().getId());
        ParticipationRequestDto requestDto = mapper.participationRequestToParticipationRequestDto(request);
        log.info("Заявка с ИД {} пользователя с ИД {} возвращена.", participationId, requestorId);
        return requestDto;
    }

    @Override
    public ParticipationRequestDto getParticipationByIdByAdmin(Long participationId) {
        ParticipationRequest request = participationHelper.isParticipationRequestPresent(participationId);
        ParticipationRequestDto requestDto = mapper.participationRequestToParticipationRequestDto(request);
        log.info("Заявка с ИД {} возвращена администратору.", participationId);
        return requestDto;
    }

    @Override
    public List<ParticipationRequestDto> getParticipationsBySelectionId(Long selectionId, int from, int size) {
        offlineSelectionValidationHelper.isOfflineSelectionPresent(selectionId);
        int page = from / size;
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        Page<ParticipationRequest> participationPage = repository.findBySelection_Id(selectionId, pageRequest);

        if (participationPage.isEmpty()) {
            log.info("Не нашлось заявок по заданным параметрам.");
            return new ArrayList<>();
        }

        List<ParticipationRequest> participations = participationPage.getContent();
        List<ParticipationRequestDto> participationDtos =
                mapper.participationRequestListToParticipationRequestDtoList(participations);
        log.info("Список заявок на участие в оффлайн-отборе с ИД {} с номера {} размером {} возвращён.", selectionId,
                from, participationDtos.size());
        return participationDtos;
    }

    @Override
    @Transactional
    public ParticipationRequestDto updateParticipationById(Long requestorId, Long participationId,
                                                           UpdateParticipationRequest updateRequest) {
        userValidationHelper.isUserPresent(requestorId);
        ParticipationRequest request = participationHelper.isParticipationRequestPresent(participationId);
        mapper.updateParticipation(updateRequest, request);
        ParticipationRequest updatedRequest = repository.save(request);
        ParticipationRequestDto participationDto = mapper.participationRequestToParticipationRequestDto(updatedRequest);
        log.info("Заявка с ID {} обновлена.", participationId);
        return participationDto;
    }

    @Override
    public ParticipationRequestStatusUpdateResult updateParticipationRequestStatusById(
            Long selectionId, ParticipationRequestStatusUpdateRequest request) {

        offlineSelectionValidationHelper.isOfflineSelectionPresent(selectionId);
        List<Long> ids = request.getRequestIds();
        List<ParticipationRequest> participations = repository.findAllById(ids);
        ParticipationRequestStatusUpdateResult result = new ParticipationRequestStatusUpdateResult();
        List<ParticipationRequest> updatedRequests = new ArrayList<>();

        for (ParticipationRequest participationRequest : participations) {
            ParticipationRequestDto participationRequestDto = mapper
                    .participationRequestToParticipationRequestDto(participationRequest);

            if (participationRequest.getStatus() == RequestStatus.PENDING) {

                if (request.getStatus() == UpdateRequestStatus.CONFIRMED) {
                    participationRequest.setStatus(RequestStatus.CONFIRMED);
                } else {
                    participationRequest.setStatus(RequestStatus.CANCELED);
                }
                updatedRequests.add(participationRequest);
                result.getUpdatedRequests().add(participationRequestDto);
            } else {
                result.getNotUpdatedRequests().add(participationRequestDto);
            }
        }

        repository.saveAll(updatedRequests);
        log.info("Статуса заявок на участие в оффлайн-отборе с ИД {} обновился у {} заявок и не обновился у {} заявок.",
                selectionId, result.getUpdatedRequests().size(), result.getNotUpdatedRequests().size());
        return result;
    }
}
