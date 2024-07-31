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

    @Override
    public ParticipationRequestDto getParticipationById(Long requestorId, Long participationId) {
        User user = userValidationHelper.isUserPresent(requestorId);
        ParticipationRequest request = helper.isParticipationRequestPresent(participationId);
        helper.isUserRequester(user, request);
        ParticipationRequestDto requestDto = mapper.participationRequestToParticipationRequestDto(request);
        log.info("Заявка с ИД {} пользователя с ИД {} возвращена.", participationId, requestorId);
        return requestDto;
    }

    @Override
    public ParticipationRequestDto getParticipationByIdByAdmin(Long participationId) {
        ParticipationRequest request = helper.isParticipationRequestPresent(participationId);
        ParticipationRequestDto requestDto = mapper.participationRequestToParticipationRequestDto(request);
        log.info("Заявка с ИД {} возвращена администратору.", participationId);
        return requestDto;
    }

    @Override
    public List<ParticipationRequestDto> getParticipationsBySelectionId(Long selectionId, int from, int size) {
        selectionValidationHelper.isSelectionPresent(selectionId);
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
        log.info("Список заявок на участие в отборе с ИД {} с номера {} размером {} возвращён.", selectionId,
                from, participationDtos.size());
        return participationDtos;
    }
}
