package com.kardoaward.kardo.participation_request.dto.update;

import com.kardoaward.kardo.enums.UpdateRequestStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Сущность обновления статуса заявки участника.")
public class ParticipationRequestStatusUpdateRequest {

    @NotNull(message = "RequestIds не может быть null.")
    private List<Long> requestIds;
    @NotNull(message = "Status не может быть null.")
    private UpdateRequestStatus status;
}
