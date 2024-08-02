package com.kardoaward.kardo.spectator_request.model.dto;

import com.kardoaward.kardo.enums.RequestStatus;
import com.kardoaward.kardo.user.model.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpectatorRequestDto {

    private Long id;
    private LocalDateTime creationTime;
    private UserDto requesterDto;
    private RequestStatus status;
}
