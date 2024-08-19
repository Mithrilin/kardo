package com.kardoaward.kardo.event.dto;

import com.kardoaward.kardo.enums.Field;
import com.kardoaward.kardo.event.model.enums.EventProgram;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEventRequest {

    @Size(min = 2, max = 250, message = "Длина title должна быть в диапазоне от 2 до 250 символов.")
    private String title;
    @Future(message = "EventStart должен быть в будущем.")
    private LocalDateTime eventStart;
    @Future(message = "EventEnd должен быть в будущем.")
    private LocalDateTime eventEnd;
    @Size(min = 2, max = 250, message = "Длина location должна быть в диапазоне от 2 до 250 символов.")
    private String location;
    private List<EventProgram> programs;
    private List<Field> fields;
    private Boolean isMainEvent;
    @Size(min = 2, max = 1500, message = "Длина Description должна быть в диапазоне от 2 до 1500 символов.")
    private String description;
}
