package com.kardoaward.kardo.selection.video_selection.model.dto;

import com.kardoaward.kardo.selection.model.dto.SelectionDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VideoSelectionDto extends SelectionDto {

    private String hashtag;
    private String description;
}
