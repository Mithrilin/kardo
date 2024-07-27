package com.kardoaward.kardo.spectatorRequest.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewSpectatorRequest {

    @NotNull(message = "EventId не может быть null.")
    private Long eventId;
    @NotNull(message = "RequesterId не может быть null.")
    private Long requesterId;
}
