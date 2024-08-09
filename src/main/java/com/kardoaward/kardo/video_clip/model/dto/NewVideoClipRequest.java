package com.kardoaward.kardo.video_clip.model.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewVideoClipRequest {

    @NotEmpty(message = "Hashtags не может быть пустым.")
    private Set<String> hashtags;
    @NotNull(message = "CreatorId не может быть null.")
    private Long creatorId;
}
