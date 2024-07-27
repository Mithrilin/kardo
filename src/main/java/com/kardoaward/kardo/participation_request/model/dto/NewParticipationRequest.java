package com.kardoaward.kardo.participation_request.model.dto;

import com.kardoaward.kardo.enums.Field;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewParticipationRequest {

    @NotNull(message = "SelectionId не может быть null.")
    private Long selectionId;
    @NotNull(message = "RequesterId не может быть null.")
    private Long requesterId;
    @NotBlank(message = "Fields не может быть пустым.")
    private List<Field> fields;
}
