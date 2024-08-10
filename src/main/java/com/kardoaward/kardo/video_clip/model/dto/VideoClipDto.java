package com.kardoaward.kardo.video_clip.model.dto;

import com.kardoaward.kardo.user.model.dto.UserShortDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VideoClipDto {

    private Long id;
    private LocalDateTime publicationTime;
    private Set<String> hashtags;
    private UserShortDto creatorDto;
    private Integer likesCount;
    private String videoLink;
}
