package com.kardoaward.kardo.user.model.dto;

import com.kardoaward.kardo.user.model.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long id;
    private String email;
    private String name;
    private String surname;
    private String patronymic;
    private LocalDate birthday;
    private String country;
    private String region;
    private String city;
    private String phoneNumber;
    private Gender gender;
    private String citizenship;
    private String profilePhoto;
    private String portfolio;
    private String aboutMe;
    private List<String> networks;
}
