package com.kardoaward.kardo.comment.model.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCommentRequest {

    @Size(min = 2, max = 7000, message = "Длина Text должна быть в диапазоне от 2 до 7000 символов.")
    private String text;
}
