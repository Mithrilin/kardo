package com.kardoaward.kardo.selection.offline_selection.model.dto;

import com.kardoaward.kardo.enums.Field;
import com.kardoaward.kardo.selection.model.dto.SelectionDto;
import com.kardoaward.kardo.selection.offline_selection.model.enums.SelectionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OfflineSelectionDto extends SelectionDto {

    private SelectionType selectionType;
    private List<Field> fields;
    private String location;
}
