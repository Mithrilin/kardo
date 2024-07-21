package com.kardoaward.kardo.user.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long id;
    @Email
    @NotBlank(message = "Email не может быть пустым.")
    private String email;
    @NotBlank(message = "Name не может быть пустым.")
    private String name;
}
