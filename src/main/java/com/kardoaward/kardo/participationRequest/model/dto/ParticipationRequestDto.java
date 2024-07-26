package com.kardoaward.kardo.participationRequest.model.dto;

import com.kardoaward.kardo.enums.Field;
import com.kardoaward.kardo.enums.RequestStatus;
import com.kardoaward.kardo.selection.model.dto.SelectionDto;
import com.kardoaward.kardo.user.model.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParticipationRequestDto {

    private Long id;
    private LocalDateTime creationTime;
    private SelectionDto selectionDto;
    private UserDto requesterDto;
    private RequestStatus status;
    private List<Field> fields;
}
