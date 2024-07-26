package com.kardoaward.kardo.offlineCompetition.model.dto;

import com.kardoaward.kardo.offlineCompetition.model.enums.Status;
import com.kardoaward.kardo.offlineCompetition.model.enums.Field;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

import static com.kardoaward.kardo.offlineCompetition.model.enums.Status.UPCOMING;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateOfflineCompetitionRequest {

    @Size(min = 2, max = 250, message = "Длина title должна быть в диапазоне от 2 до 250 символов.")
    private String title;
    @Future(message = "CompetitionStart должен быть в будущем.")
    private LocalDate competitionStart;
    @Future(message = "CompetitionEnd должен быть в будущем.")
    private LocalDate competitionEnd;
    private Status status = UPCOMING;
    @Size(min = 2, max = 250, message = "Длина location должна быть в диапазоне от 2 до 250 символов.")
    private String location;
    private List<Field> fields;
    @Size(min = 2, max = 5000, message = "Длина Description должна быть в диапазоне от 2 до 5000 символов.")
    private String description;
}
