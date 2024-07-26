package com.kardoaward.kardo.onlineCompetition.model.dto;

import com.kardoaward.kardo.enums.Status;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateOnlineCompetitionRequest {

    @Size(min = 2, max = 250, message = "Длина title должна быть в диапазоне от 2 до 250 символов.")
    private String title;
    @Future(message = "CompetitionStart должен быть в будущем.")
    private LocalDate competitionStart;
    @Future(message = "CompetitionEnd должен быть в будущем.")
    private LocalDate competitionEnd;
    private Status status;
    @Size(min = 2, max = 10000, message = "Длина Description должна быть в диапазоне от 2 до 10000 символов.")
    private String description;
}
