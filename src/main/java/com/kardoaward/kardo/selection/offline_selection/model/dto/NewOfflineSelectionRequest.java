package com.kardoaward.kardo.selection.offline_selection.model.dto;

import com.kardoaward.kardo.enums.Field;
import com.kardoaward.kardo.selection.model.dto.NewSelectionRequest;
import com.kardoaward.kardo.selection.offline_selection.model.enums.SelectionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewOfflineSelectionRequest extends NewSelectionRequest {

    @NotNull(message = "SelectionType не может быть null.")
    private SelectionType selectionType;
    @NotEmpty(message = "Fields не может быть пустым.")
    private List<Field> fields;
    @NotBlank(message = "Location не может быть пустым.")
    @Size(min = 2, max = 250, message = "Длина location должна быть в диапазоне от 2 до 250 символов.")
    private String location;
}
