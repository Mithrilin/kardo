package com.kardoaward.kardo.spectator_request.dto.update;

import com.kardoaward.kardo.enums.UpdateRequestStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Сущность обновления статуса заявки зрителя.")
public class SpectatorRequestStatusUpdateRequest {

    @NotEmpty(message = "RequestIds не может быть пустым.")
    private List<Long> requestIds;
    @NotNull(message = "Status не может быть null.")
    private UpdateRequestStatus status;
}
