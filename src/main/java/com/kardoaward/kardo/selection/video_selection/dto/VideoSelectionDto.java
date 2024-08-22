package com.kardoaward.kardo.selection.video_selection.dto;

import com.kardoaward.kardo.selection.dto.SelectionDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Сущность видео-отбора.")
public class VideoSelectionDto extends SelectionDto {

    private String hashtag;
    private String description;
}
