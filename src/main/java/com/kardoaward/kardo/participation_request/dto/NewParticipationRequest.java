package com.kardoaward.kardo.participation_request.dto;

import com.kardoaward.kardo.enums.Field;
import jakarta.validation.constraints.NotEmpty;
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
    @NotEmpty(message = "Fields не может быть пустым.")
    private List<Field> fields;
}
