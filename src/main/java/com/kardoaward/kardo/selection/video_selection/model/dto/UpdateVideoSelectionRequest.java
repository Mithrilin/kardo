package com.kardoaward.kardo.selection.video_selection.model.dto;

import com.kardoaward.kardo.selection.model.dto.UpdateSelectionRequest;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateVideoSelectionRequest extends UpdateSelectionRequest {

    @Size(min = 2, max = 6500, message = "Длина Description должна быть в диапазоне от 2 до 6500 символов.")
    private String description;
}
