package com.kardoaward.kardo.selection.dto;

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
public class UpdateSelectionRequest {

    @Size(min = 2, max = 250, message = "Длина title должна быть в диапазоне от 2 до 250 символов.")
    private String title;
    @Future(message = "SelectionStart должен быть в будущем.")
    private LocalDate selectionStart;
    @Future(message = "SelectionEnd должен быть в будущем.")
    private LocalDate selectionEnd;
    private Status status;
}
