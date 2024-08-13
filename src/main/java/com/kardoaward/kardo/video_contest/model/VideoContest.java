package com.kardoaward.kardo.video_contest.model;

import com.kardoaward.kardo.enums.Status;
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

import static com.kardoaward.kardo.enums.Status.UPCOMING;

@Entity
@Table(name = "video_contests")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class VideoContest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String hashtag;
    @Column(name = "contest_start")
    private LocalDate contestStart;
    @Column(name = "contest_end")
    private LocalDate contestEnd;
    @Enumerated(EnumType.STRING)
    private Status status = UPCOMING;
    private String description;
}
