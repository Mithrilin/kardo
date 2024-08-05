package com.kardoaward.kardo.online_competition.model.dto;

import com.kardoaward.kardo.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OnlineCompetitionDto {

    private Long id;
    private String title;
    private String hashtag;
    private LocalDate competitionStart;
    private LocalDate competitionEnd;
    private Status status;
    private String description;
}
