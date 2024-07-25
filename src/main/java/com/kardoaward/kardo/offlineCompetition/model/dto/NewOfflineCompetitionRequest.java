package com.kardoaward.kardo.offlineCompetition.model.dto;

import com.kardoaward.kardo.offlineCompetition.model.enums.EventStatus;
import com.kardoaward.kardo.offlineCompetition.model.enums.Field;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

import static com.kardoaward.kardo.offlineCompetition.model.enums.EventStatus.UPCOMING;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewOfflineCompetitionRequest {

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
    private EventStatus status = UPCOMING;
    @NotBlank(message = "Location не может быть пустым.")
    @Size(min = 2, max = 250, message = "Длина location должна быть в диапазоне от 2 до 250 символов.")
    private String location;
    @NotNull(message = "Fields не может быть null.")
    private List<Field> fields;
    @NotBlank(message = "Description не может быть пустым.")
    @Size(min = 2, max = 5000, message = "Длина Description должна быть в диапазоне от 2 до 5000 символов.")
    private String description;
}
