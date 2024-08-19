package com.kardoaward.kardo.comment.dto;

import com.kardoaward.kardo.user.dto.UserShortDto;
import com.kardoaward.kardo.video_clip.dto.VideoClipDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Сущность комментария к видео-клипу.")
public class CommentDto {

    private Long id;
    private UserShortDto authorDto;
    private LocalDateTime publicationTime;
    private VideoClipDto videoClipDto;
    private String text;
}
