package com.kardoaward.kardo.participationRequest.model.dto;

import com.kardoaward.kardo.enums.Field;
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
    @NotNull(message = "Fields не может быть null.")
    private List<Field> fields;
}
