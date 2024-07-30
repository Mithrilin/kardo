package com.kardoaward.kardo.selection.model.dto;

import com.kardoaward.kardo.enums.Field;
import com.kardoaward.kardo.selection.model.enums.SelectionType;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewSelectionRequest {

    @NotBlank(message = "Title не может быть пустым.")
    @Size(min = 2, max = 250, message = "Длина title должна быть в диапазоне от 2 до 250 символов.")
    private String title;
    @NotBlank(message = "Hashtag не может быть пустым.")
    @Size(min = 2, max = 20, message = "Длина hashtag должна быть в диапазоне от 2 до 20 символов.")
    private String hashtag;
    @NotNull(message = "SelectionType не может быть null.")
    private SelectionType selectionType;
    @NotNull(message = "CompetitionId не может быть null.")
    private Long competitionId;
    @NotNull(message = "SelectionStart не может быть null.")
    @Future(message = "SelectionStart должен быть в будущем.")
    private LocalDate selectionStart;
    @NotNull(message = "SelectionEnd не может быть null.")
    @Future(message = "SelectionEnd должен быть в будущем.")
    private LocalDate selectionEnd;
    @NotEmpty(message = "Fields не может быть пустым.")
    private List<Field> fields;
    @NotBlank(message = "Location не может быть пустым.")
    @Size(min = 2, max = 250, message = "Длина location должна быть в диапазоне от 2 до 250 символов.")
    private String location;
}
