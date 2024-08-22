package com.kardoaward.kardo.video_clip.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateVideoClipRequest {

    @NotEmpty(message = "Hashtags не может быть пустым.")
    private Set<String> hashtags;
}
