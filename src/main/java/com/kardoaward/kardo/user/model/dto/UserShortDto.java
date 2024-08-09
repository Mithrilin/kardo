package com.kardoaward.kardo.user.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserShortDto {

    private Long id;
    private String name;
    private String surname;
    private LocalDate birthday;
    private String country;
    //ToDo Нужно ли возвращать ссылку на аватарку?
    private String avatarPhoto;
}
