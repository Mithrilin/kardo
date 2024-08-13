package com.kardoaward.kardo.security;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Сущность получаемая при авторизации.")
public class AuthenticationRequestDTO {

    private String email;
    private String password;
}
