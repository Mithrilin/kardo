//package com.kardoaward.kardo.config;
//
//import com.kardoaward.kardo.user.service.MyUserDetailsService;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableWebSecurity
//@EnableMethodSecurity
//public class SecurityConfig{
//
//    @Bean
//    public UserDetailsService userDetailsService() { //создаем user и сохраняем его в памяти
////        UserDetails admin = User.builder().username("admin").password(passwordEncoder().encode("passAdmin")) //тут можем прописывать пользователей, их роли
////                .roles("ADMIN").build(); // и пароль. passwordEncoder().encode - шифруем пароль
//        return new MyUserDetailsService(); // создаем user через контроллер, а не руками. Здесь - работаем через MyUserDetailsService
//    }
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{ // HttpSecurity - конфигурируем авторизацию и аутентификацию HTTP запросов
//        return http.csrf(AbstractHttpConfigurer::disable) // отключили защиту от csrf-атак
//                .authorizeHttpRequests(auth -> auth.requestMatchers("/users/welcome", "/users/reg").permitAll() //аргументом принимает строку/строки, содержащую url
//                                                                                                                  //эти строки доступны всем - permitAll
//                        .requestMatchers("/users/**").authenticated()) // доступ только для залогинившихся
//                .formLogin(AbstractAuthenticationFilterConfigurer::permitAll) //даем доступ к форме авторизации всем - permitAll
//                .build();
//    }
//
//    @Bean
//    public AuthenticationProvider authenticationProvider() { // проводит аутентификацию логина и пароля - отвечаем на вопрос "Кто вы", подтвержаем личность пользователя
//        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(); // создали provider дл аутентификации
//        provider.setUserDetailsService(userDetailsService());
//        provider.setPasswordEncoder(passwordEncoder());
//        return provider;
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() { // одностороннее преобразование пароля
//        return new BCryptPasswordEncoder();
//    }
//}