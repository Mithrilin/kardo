package com.kardoaward.kardo.selection.video_selection.model.dto;

import com.kardoaward.kardo.selection.model.dto.NewSelectionRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewVideoSelectionRequest extends NewSelectionRequest {

    @NotBlank(message = "Hashtag не может быть пустым.")
    @Size(min = 2, max = 20, message = "Длина hashtag должна быть в диапазоне от 2 до 20 символов.")
    private String hashtag;
    @NotBlank(message = "Description не может быть пустым.")
    @Size(min = 2, max = 6500, message = "Длина Description должна быть в диапазоне от 2 до 6500 символов.")
    private String description;
}
