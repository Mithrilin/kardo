package com.kardoaward.kardo.auth;

import com.kardoaward.kardo.user.model.enums.Gender;
import com.kardoaward.kardo.user.model.enums.Role;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String name;
    private String surname;
    private String patronymic;
    private LocalDate birthday;
    private String country;
    private String region;
    private String city;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String citizenship;
    @Column(name = "profile_photo")
    private String profilePhoto;
    private String portfolio;
    @Column(name = "about_me")
    private String aboutMe;
    //    @ElementCollection
//    @CollectionTable(name="user_networks",
//            joinColumns=@JoinColumn(name="user_id"))
//  private Set<String> network;
    @Enumerated(EnumType.STRING)
    private Role roles;
    private String password;
}
