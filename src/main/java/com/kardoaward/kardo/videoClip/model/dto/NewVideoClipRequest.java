package com.kardoaward.kardo.videoClip.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @NotBlank(message = "VideoLink не может быть пустым.")
    @Size(min = 2, max = 250, message = "Длина VideoLink должна быть в диапазоне от 2 до 250 символов.")
    private String videoLink;
}
