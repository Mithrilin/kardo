package com.kardoaward.kardo.selection.model.dto;

import com.kardoaward.kardo.enums.Field;
import com.kardoaward.kardo.enums.Status;
import com.kardoaward.kardo.offline_competition.model.dto.OfflineCompetitionDto;
import com.kardoaward.kardo.selection.model.enums.SelectionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SelectionDto {

    private Long id;
    private String title;
    private String hashtag;
    private SelectionType selectionType;
    private OfflineCompetitionDto competitionDto;
    private LocalDate selectionStart;
    private LocalDate selectionEnd;
    private Status status;
    private List<Field> fields;
    private String location;
}
