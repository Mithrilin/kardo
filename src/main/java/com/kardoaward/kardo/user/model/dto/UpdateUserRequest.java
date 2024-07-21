package com.kardoaward.kardo.user.model.dto;

import com.kardoaward.kardo.user.model.enums.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequest {

    @Email
    @Size(min = 6, max = 250, message = "Длина email должна быть в диапазоне от 6 до 250 символов.")
    private String email;
    @Size(min = 2, max = 250, message = "Длина name должна быть в диапазоне от 2 до 250 символов.")
    private String name;
    @Size(min = 2, max = 250, message = "Длина surname должна быть в диапазоне от 2 до 250 символов.")
    private String surname;
    @Size(min = 2, max = 250, message = "Длина patronymic должна быть в диапазоне от 2 до 250 символов.")
    private String patronymic;
    @Past(message = "Birthday должен быть в прошлом.")
    private LocalDate birthday;
    @Size(min = 2, max = 250, message = "Длина country должна быть в диапазоне от 2 до 250 символов.")
    private String country;
    @Size(min = 2, max = 250, message = "Длина region должна быть в диапазоне от 2 до 250 символов.")
    private String region;
    @Size(min = 2, max = 250, message = "Длина city должна быть в диапазоне от 2 до 250 символов.")
    private String city;
    //ToDo Как валидировать телефонный номер?
    @Size(min = 2, max = 250, message = "Длина phoneNumber должна быть в диапазоне от 2 до 250 символов.")
    private String phoneNumber;
    private Gender gender;
    @Size(min = 2, max = 250, message = "Длина citizenship должна быть в диапазоне от 2 до 250 символов.")
    private String citizenship;
    //ToDo Как валидировать ссылки?
    @URL(message = "ProfilePhoto должен быть ссылкой.")
    private String profilePhoto;
    @URL(message = "Portfolio должен быть ссылкой.")
    private String portfolio;
    @Size(min = 2, max = 10000, message = "Длина aboutMe должна быть в диапазоне от 2 до 10000 символов.")
    private String aboutMe;
    //ToDo Исправить на список. В БД это поле отсутствует.
    private String network;
}
