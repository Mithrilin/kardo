package com.kardoaward.kardo.selection.offline_selection.dto;

import com.kardoaward.kardo.enums.Field;
import com.kardoaward.kardo.selection.dto.SelectionDto;
import com.kardoaward.kardo.selection.offline_selection.enums.SelectionType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Сущность оффлайн-отбора.")
public class OfflineSelectionDto extends SelectionDto {

    private SelectionType selectionType;
    private List<Field> fields;
    private String location;
}
