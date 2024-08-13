package com.kardoaward.kardo.participation_request.model.dto;

import com.kardoaward.kardo.enums.Field;
import com.kardoaward.kardo.enums.RequestStatus;
import com.kardoaward.kardo.selection.offline_selection.model.dto.OfflineSelectionDto;
import com.kardoaward.kardo.user.model.dto.UserShortDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Сущность заявки на участие в отборе.")
public class ParticipationRequestDto {

    private Long id;
    private LocalDateTime creationTime;
    private OfflineSelectionDto selectionDto;
    private UserShortDto requesterDto;
    private RequestStatus status;
    private List<Field> fields;
}
