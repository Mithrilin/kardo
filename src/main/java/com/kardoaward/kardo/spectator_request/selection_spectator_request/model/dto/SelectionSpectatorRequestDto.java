package com.kardoaward.kardo.spectator_request.selection_spectator_request.model.dto;

import com.kardoaward.kardo.selection.offline_selection.model.dto.OfflineSelectionDto;
import com.kardoaward.kardo.spectator_request.model.dto.SpectatorRequestDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SelectionSpectatorRequestDto extends SpectatorRequestDto {

    private OfflineSelectionDto selectionDto;
}
