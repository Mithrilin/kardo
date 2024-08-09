package com.kardoaward.kardo.user.model;

import com.kardoaward.kardo.user.model.enums.Gender;
import com.kardoaward.kardo.user.model.enums.Role;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
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
    @Column(name = "avatar_photo")
    private String avatarPhoto;
    private String portfolio;
    @Column(name = "about_me")
    private String aboutMe;
    @ElementCollection
    @CollectionTable(name="user_networks",
            joinColumns=@JoinColumn(name="user_id"))
    private Set<String> network;
    @Enumerated(EnumType.STRING)
    private Role role;
}
