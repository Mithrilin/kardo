package com.kardoaward.kardo.event.model.dto;

import com.kardoaward.kardo.enums.Field;
import com.kardoaward.kardo.event.model.enums.EventProgram;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewEventRequest {

    @NotBlank(message = "Title не может быть пустым.")
    @Size(min = 2, max = 250, message = "Длина title должна быть в диапазоне от 2 до 250 символов.")
    private String title;
    @NotNull(message = "CompetitionId не может быть null.")
    private Long competitionId;
    @NotNull(message = "EventStart не может быть null.")
    @Future(message = "EventStart должен быть в будущем.")
    private LocalDateTime eventStart;
    @NotNull(message = "EventEnd не может быть null.")
    @Future(message = "EventEnd должен быть в будущем.")
    private LocalDateTime eventEnd;
    @NotBlank(message = "Location не может быть пустым.")
    @Size(min = 2, max = 250, message = "Длина location должна быть в диапазоне от 2 до 250 символов.")
    private String location;
    @NotEmpty(message = "Programs не может быть пустым.")
    private List<EventProgram> programs;
    @NotEmpty(message = "Fields не может быть пустым.")
    private List<Field> fields;
    @NotBlank(message = "Logo не может быть пустым.")
    @Size(min = 2, max = 250, message = "Длина Logo должна быть в диапазоне от 2 до 250 символов.")
    private String logo;
    @NotNull(message = "IsMainEvent не может быть null.")
    private Boolean isMainEvent;
    @NotBlank(message = "Description не может быть пустым.")
    @Size(min = 2, max = 5000, message = "Длина Description должна быть в диапазоне от 2 до 5000 символов.")
    private String description;
}
