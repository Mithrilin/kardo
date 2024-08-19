package com.kardoaward.kardo.video_contest.dto;

import com.kardoaward.kardo.enums.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Сущность видео-соревнования.")
public class VideoContestDto {

    private Long id;
    private String title;
    private String hashtag;
    private LocalDate contestStart;
    private LocalDate contestEnd;
    private Status status;
    private String description;
}
