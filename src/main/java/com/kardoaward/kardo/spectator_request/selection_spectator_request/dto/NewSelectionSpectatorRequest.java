package com.kardoaward.kardo.spectator_request.selection_spectator_request.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewSelectionSpectatorRequest {

    @NotNull(message = "SelectionId не может быть null.")
    private Long selectionId;
    @NotNull(message = "RequesterId не может быть null.")
    private Long requesterId;
}
