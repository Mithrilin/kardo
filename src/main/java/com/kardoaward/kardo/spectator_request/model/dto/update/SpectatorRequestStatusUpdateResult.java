package com.kardoaward.kardo.spectator_request.model.dto.update;

import com.kardoaward.kardo.spectator_request.model.dto.SpectatorRequestDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Возвращаемая сущность при обновления статуса заявки зрителя.")
public class SpectatorRequestStatusUpdateResult {

    private List<SpectatorRequestDto> updatedRequests;
    private List<SpectatorRequestDto> notUpdatedRequests;
}
