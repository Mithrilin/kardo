package com.kardoaward.kardo.participationRequest.model.dto.update;

import com.kardoaward.kardo.enums.UpdateRequestStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParticipationRequestStatusUpdateRequest {

    @NotNull(message = "RequestIds не может быть null.")
    private List<Long> requestIds;
    @NotNull(message = "Status не может быть null.")
    private UpdateRequestStatus status;
}
