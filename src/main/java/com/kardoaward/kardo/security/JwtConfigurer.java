package com.kardoaward.kardo.security;

import org.springframework.security.config.annotation.SecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component
public class JwtConfigurer implements SecurityConfigurer<DefaultSecurityFilterChain, HttpSecurity> {

    private final JwtTokenFilter jwtTokenFilter;

    public JwtConfigurer(JwtTokenFilter jwtTokenFilter) {
        this.jwtTokenFilter = jwtTokenFilter;
    }

    @Override
    public void init(HttpSecurity builder) throws Exception {
    }

    @Override
    public void configure(HttpSecurity httpSecurity) {
        httpSecurity.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
