package com.kardoaward.kardo.selection.dto;

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
public class NewSelectionRequest {

    @NotBlank(message = "Title не может быть пустым.")
    @Size(min = 2, max = 250, message = "Длина title должна быть в диапазоне от 2 до 250 символов.")
    private String title;
    @NotNull(message = "CompetitionId не может быть null.")
    private Long competitionId;
    @NotNull(message = "SelectionStart не может быть null.")
    @Future(message = "SelectionStart должен быть в будущем.")
    private LocalDate selectionStart;
    @NotNull(message = "SelectionEnd не может быть null.")
    @Future(message = "SelectionEnd должен быть в будущем.")
    private LocalDate selectionEnd;
}
