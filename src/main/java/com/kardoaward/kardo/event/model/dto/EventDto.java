package com.kardoaward.kardo.event.model.dto;

import com.kardoaward.kardo.enums.Field;
import com.kardoaward.kardo.enums.Status;
import com.kardoaward.kardo.event.model.enums.EventProgram;
import com.kardoaward.kardo.grand_competition.model.dto.GrandCompetitionDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventDto {

    private Long id;
    private String title;
    private GrandCompetitionDto competitionDto;
    private LocalDateTime eventStart;
    private LocalDateTime eventEnd;
    private String location;
    private Status status;
    private List<EventProgram> programs;
    private List<Field> fields;
    //ToDo Нужно ли возвращать ссылку на логотип?
    private String logo;
    private Boolean isMainEvent;
    private String description;
}
