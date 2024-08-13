package com.kardoaward.kardo.spectator_request.event_spectator_request.model.dto;

import com.kardoaward.kardo.event.model.dto.EventShortDto;
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
@Schema(description = "Сущность заявки зрителя на мероприятие.")
public class EventSpectatorRequestDto extends SpectatorRequestDto {

    private EventShortDto eventDto;
}
