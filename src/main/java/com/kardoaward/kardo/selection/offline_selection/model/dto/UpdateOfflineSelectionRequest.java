package com.kardoaward.kardo.selection.offline_selection.model.dto;

import com.kardoaward.kardo.enums.Field;
import com.kardoaward.kardo.selection.model.dto.UpdateSelectionRequest;
import com.kardoaward.kardo.selection.offline_selection.model.enums.SelectionType;
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
public class UpdateOfflineSelectionRequest extends UpdateSelectionRequest {

    private SelectionType selectionType;
    private List<Field> fields;
    @Size(min = 2, max = 250, message = "Длина location должна быть в диапазоне от 2 до 250 символов.")
    private String location;
}
