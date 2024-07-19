package com.kardoaward.kardo.user.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewUserRequest {

    @Email
    @NotBlank(message = "Email не может быть пустым.")
    @Length(min = 6, max = 250, message = "Длина email должна быть в диапазоне от 6 до 250 символов.")
    private String email;
    @NotBlank(message = "Name не может быть пустым.")
    @Length(min = 2, max = 250, message = "Длина name должна быть в диапазоне от 2 до 250 символов.")
    private String name;
}
