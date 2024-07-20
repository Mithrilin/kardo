package com.kardoaward.kardo.user.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewUserRequest {

    @Email
    @NotBlank(message = "Email не может быть пустым.")
    @Size(min = 6, max = 250, message = "Длина email должна быть в диапазоне от 6 до 250 символов.")
    private String email;
    @NotBlank(message = "Name не может быть пустым.")
    @Size(min = 2, max = 250, message = "Длина name должна быть в диапазоне от 2 до 250 символов.")
    private String name;
    @NotBlank(message = "Surname не может быть пустым.")
    @Size(min = 2, max = 250, message = "Длина surname должна быть в диапазоне от 2 до 250 символов.")
    private String surname;
    @Size(min = 2, max = 250, message = "Длина patronymic должна быть в диапазоне от 2 до 250 символов.")
    private String patronymic;
    @NotNull(message = "Birthday не может быть null.")
    @Past
    private LocalDate birthday;
    @NotBlank(message = "Country не может быть пустым.")
    @Size(min = 2, max = 250, message = "Длина country должна быть в диапазоне от 2 до 250 символов.")
    private String country;
    @NotBlank(message = "Region не может быть пустым.")
    @Size(min = 2, max = 250, message = "Длина region должна быть в диапазоне от 2 до 250 символов.")
    private String region;
    @NotBlank(message = "City не может быть пустым.")
    @Size(min = 2, max = 250, message = "Длина city должна быть в диапазоне от 2 до 250 символов.")
    private String city;
    private List<String> networks;
}
