package com.kardoaward.kardo.video_contest.model.dto;

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
public class UpdateVideoContestRequest {

    @Size(min = 2, max = 250, message = "Длина title должна быть в диапазоне от 2 до 250 символов.")
    private String title;
    @Future(message = "ContestStart должен быть в будущем.")
    private LocalDate contestStart;
    @Future(message = "ContestEnd должен быть в будущем.")
    private LocalDate contestEnd;
    private Status status;
    @Size(min = 2, max = 10000, message = "Длина Description должна быть в диапазоне от 2 до 10000 символов.")
    private String description;
}
