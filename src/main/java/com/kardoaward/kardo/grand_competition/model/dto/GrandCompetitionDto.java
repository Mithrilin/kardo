package com.kardoaward.kardo.grand_competition.model.dto;

import com.kardoaward.kardo.enums.Status;
import com.kardoaward.kardo.enums.Field;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GrandCompetitionDto {

    private Long id;
    private String title;
    private String hashtag;
    private LocalDate competitionStart;
    private LocalDate competitionEnd;
    private Status status;
    private String location;
    private List<Field> fields;
    private String description;
}
