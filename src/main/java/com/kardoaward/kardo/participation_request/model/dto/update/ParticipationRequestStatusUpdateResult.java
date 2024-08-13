package com.kardoaward.kardo.participation_request.model.dto.update;

import com.kardoaward.kardo.participation_request.model.dto.ParticipationRequestDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Возвращаемая сущность обновления статуса заявки участника.")
public class ParticipationRequestStatusUpdateResult {

    private List<ParticipationRequestDto> updatedRequests;
    private List<ParticipationRequestDto> notUpdatedRequests;
}
