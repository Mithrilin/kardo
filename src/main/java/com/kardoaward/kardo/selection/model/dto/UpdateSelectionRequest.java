package com.kardoaward.kardo.selection.model.dto;

import com.kardoaward.kardo.enums.Field;
import com.kardoaward.kardo.enums.Status;
import com.kardoaward.kardo.selection.model.enums.SelectionType;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateSelectionRequest {

    @Size(min = 2, max = 250, message = "Длина title должна быть в диапазоне от 2 до 250 символов.")
    private String title;
    private SelectionType selectionType;
    @Future(message = "SelectionStart должен быть в будущем.")
    private LocalDate selectionStart;
    @Future(message = "SelectionEnd должен быть в будущем.")
    private LocalDate selectionEnd;
    private Status status;
    private List<Field> fields;
    @Size(min = 2, max = 250, message = "Длина location должна быть в диапазоне от 2 до 250 символов.")
    private String location;
}
