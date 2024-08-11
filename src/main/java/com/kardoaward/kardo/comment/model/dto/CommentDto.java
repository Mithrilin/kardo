package com.kardoaward.kardo.comment.model.dto;

import com.kardoaward.kardo.user.model.dto.UserShortDto;
import com.kardoaward.kardo.video_clip.model.dto.VideoClipDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {

    private Long id;
    private UserShortDto authorDto;
    private LocalDateTime publicationTime;
    private VideoClipDto videoClipDto;
    private String text;
}
