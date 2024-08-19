package com.kardoaward.kardo.selection.dto;

import com.kardoaward.kardo.enums.Status;
import com.kardoaward.kardo.grand_competition.dto.GrandCompetitionDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SelectionDto {

    private Long id;
    private String title;
    private GrandCompetitionDto competitionDto;
    private LocalDate selectionStart;
    private LocalDate selectionEnd;
    private Status status;
}
