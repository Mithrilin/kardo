package com.kardoaward.kardo.onlineCompetition.model;

import com.kardoaward.kardo.offlineCompetition.model.enums.Status;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Table(name = "online_competitions")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class OnlineCompetition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String hashtag;
    @Column(name = "competition_start")
    private LocalDate competitionStart;
    @Column(name = "competition_end")
    private LocalDate competitionEnd;
    @Enumerated(EnumType.STRING)
    private Status status;
    private String description;
}
