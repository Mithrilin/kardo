package com.kardoaward.kardo.participation_request.dto.update;

import com.kardoaward.kardo.enums.Field;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateParticipationRequest {

    @NotNull(message = "Fields не может быть null.")
    private List<Field> fields;
}
