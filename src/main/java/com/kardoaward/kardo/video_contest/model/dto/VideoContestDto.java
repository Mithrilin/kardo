package com.kardoaward.kardo.video_contest.model.dto;

import com.kardoaward.kardo.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VideoContestDto {

    private Long id;
    private String title;
    private String hashtag;
    private LocalDate contestStart;
    private LocalDate contestEnd;
    private Status status;
    private String description;
}
