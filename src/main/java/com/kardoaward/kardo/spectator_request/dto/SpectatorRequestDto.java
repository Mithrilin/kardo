package com.kardoaward.kardo.spectator_request.dto;

import com.kardoaward.kardo.enums.RequestStatus;
import com.kardoaward.kardo.user.dto.UserShortDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Сущность заявки зрителя мероприятия.")
public class SpectatorRequestDto {

    private Long id;
    private LocalDateTime creationTime;
    private UserShortDto requesterDto;
    private RequestStatus status;
}
