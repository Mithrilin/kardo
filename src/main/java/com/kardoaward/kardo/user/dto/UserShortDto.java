package com.kardoaward.kardo.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Сущность пользователя короткая.")
public class UserShortDto {

    private Long id;
    private String name;
    private String surname;
    private LocalDate birthday;
    private String country;
    private String avatar;
}
