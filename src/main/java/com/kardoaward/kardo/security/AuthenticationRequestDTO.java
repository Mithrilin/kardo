package com.kardoaward.kardo.security;

import lombok.Data;

@Data
public class AuthenticationRequestDTO {

    private String email;
    private String password;
}
