package com.kardoaward.kardo.onlineCompetition.model.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewOnlineCompetitionRequest {

    @NotBlank(message = "Title не может быть пустым.")
    @Size(min = 2, max = 250, message = "Длина title должна быть в диапазоне от 2 до 250 символов.")
    private String title;
    @NotBlank(message = "Hashtag не может быть пустым.")
    @Size(min = 2, max = 20, message = "Длина hashtag должна быть в диапазоне от 2 до 20 символов.")
    private String hashtag;
    @NotNull(message = "CompetitionStart не может быть null.")
    @Future(message = "CompetitionStart должен быть в будущем.")
    private LocalDate competitionStart;
    @NotNull(message = "CompetitionEnd не может быть null.")
    @Future(message = "CompetitionEnd должен быть в будущем.")
    private LocalDate competitionEnd;
    @NotBlank(message = "Description не может быть пустым.")
    @Size(min = 2, max = 10000, message = "Длина Description должна быть в диапазоне от 2 до 10000 символов.")
    private String description;
}
