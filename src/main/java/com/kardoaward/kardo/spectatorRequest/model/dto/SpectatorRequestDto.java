package com.kardoaward.kardo.spectatorRequest.model.dto;

import com.kardoaward.kardo.enums.RequestStatus;
import com.kardoaward.kardo.event.model.Event;
import com.kardoaward.kardo.user.model.User;
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
    private Event event;
    private User requester;
    private RequestStatus status;
}
