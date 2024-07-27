package com.kardoaward.kardo.spectator_request.model.dto.update;

import com.kardoaward.kardo.spectator_request.model.dto.SpectatorRequestDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpectatorRequestStatusUpdateResult {

    private List<SpectatorRequestDto> updatedRequests;
    private List<SpectatorRequestDto> notUpdatedRequests;
}
