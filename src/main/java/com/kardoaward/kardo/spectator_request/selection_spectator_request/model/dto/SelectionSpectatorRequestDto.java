package com.kardoaward.kardo.spectator_request.selection_spectator_request.model.dto;

import com.kardoaward.kardo.selection.offline_selection.model.dto.OfflineSelectionDto;
import com.kardoaward.kardo.spectator_request.model.dto.SpectatorRequestDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Сущность заявки зрителя на отбор.")
public class SelectionSpectatorRequestDto extends SpectatorRequestDto {

    private OfflineSelectionDto selectionDto;
}
