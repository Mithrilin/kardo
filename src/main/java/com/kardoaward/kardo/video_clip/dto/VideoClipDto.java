package com.kardoaward.kardo.video_clip.dto;

import com.kardoaward.kardo.user.dto.UserShortDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Сущность видео-клипа.")
public class VideoClipDto {

    private Long id;
    private LocalDateTime publicationTime;
    private Set<String> hashtags;
    private UserShortDto creatorDto;
    private Integer likesCount;
    private String video;
}
