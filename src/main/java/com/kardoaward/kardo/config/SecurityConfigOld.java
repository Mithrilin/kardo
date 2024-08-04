//package com.kardoaward.kardo.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//public class SecurityConfig {
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                // Configure authorization rules
//                .authorizeRequests()
//                // Allow access to any URL under /public/ without authentication
//                .antMatchers("/public/**").permitAll()
//                // Require authentication for any other request
//                .anyRequest().authenticated()
//                .and()
//                // Configure form-based login
//                .formLogin()
//                // Specify the custom login page URL
//                .loginPage("/login")
//                // Allow everyone to access the login page
//                .permitAll()
//                .and()
//                // Configure logout functionality
//                .logout()
//                // Allow everyone to access the logout functionality
//                .permitAll();
//
//        // Return the configured SecurityFilterChain
//        return http.build();
//    }
//}
