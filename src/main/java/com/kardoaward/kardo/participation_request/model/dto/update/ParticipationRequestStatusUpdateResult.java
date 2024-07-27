package com.kardoaward.kardo.participation_request.model.dto.update;

import com.kardoaward.kardo.participation_request.model.dto.ParticipationRequestDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParticipationRequestStatusUpdateResult {

    private List<ParticipationRequestDto> updatedRequests;
    private List<ParticipationRequestDto> notUpdatedRequests;
}
