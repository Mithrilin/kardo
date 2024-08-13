package com.kardoaward.kardo.grand_competition.model.dto;

import com.kardoaward.kardo.enums.Status;
import com.kardoaward.kardo.enums.Field;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateGrandCompetitionRequest {

    @Size(min = 2, max = 250, message = "Длина title должна быть в диапазоне от 2 до 250 символов.")
    private String title;
    @Future(message = "CompetitionStart должен быть в будущем.")
    private LocalDate competitionStart;
    @Future(message = "CompetitionEnd должен быть в будущем.")
    private LocalDate competitionEnd;
    private Status status;
    @Size(min = 2, max = 250, message = "Длина location должна быть в диапазоне от 2 до 250 символов.")
    private String location;
    private List<Field> fields;
    @Size(min = 2, max = 1000, message = "Длина Description должна быть в диапазоне от 2 до 1000 символов.")
    private String description;
}
