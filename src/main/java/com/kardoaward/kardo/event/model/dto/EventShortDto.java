package com.kardoaward.kardo.event.model.dto;

import com.kardoaward.kardo.enums.Status;
import com.kardoaward.kardo.grand_competition.model.dto.GrandCompetitionDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Сущность мероприятия короткая.")
public class EventShortDto {

    private Long id;
    private String title;
    private GrandCompetitionDto competitionDto;
    private LocalDateTime eventStart;
    private LocalDateTime eventEnd;
    private Status status;
    private String logo;
}
