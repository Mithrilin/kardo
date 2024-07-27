package com.kardoaward.kardo.selection.service.helper;

import com.kardoaward.kardo.exception.BadRequestException;
import com.kardoaward.kardo.exception.NotFoundException;
import com.kardoaward.kardo.selection.model.Selection;
import com.kardoaward.kardo.selection.model.dto.UpdateSelectionRequest;
import com.kardoaward.kardo.selection.repository.SelectionRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class SelectionValidationHelper {

    private final SelectionRepository selectionRepository;

    public Selection isSelectionPresent(Long selectionId) {
        Optional<Selection> optionalSelection = selectionRepository.findById(selectionId);

        if (optionalSelection.isEmpty()) {
            log.error("Отбор с ИД {} отсутствует в БД.", selectionId);
            throw new NotFoundException(String.format("Отбор с ИД %d отсутствует в БД.", selectionId));
        }

        return optionalSelection.get();
    }

    public void isUpdateSelectionDateValid(Selection selection, UpdateSelectionRequest request) {
        if (request.getSelectionStart() != null
                && request.getSelectionEnd() != null
                && request.getSelectionEnd().isBefore(request.getSelectionStart())) {
            log.error("Дата и время начала отбора не может быть после его конца.");
            throw new BadRequestException("Дата и время начала отбора не может быть после его конца.");
        }

        if (request.getSelectionStart() != null
                && request.getSelectionEnd() == null
                && request.getSelectionStart().isAfter(selection.getSelectionEnd())) {
            log.error("Дата и время начала отбора не может быть в после его конца.");
            throw new BadRequestException("Дата и время начала отбора не может быть в после его конца.");
        }

        if (request.getSelectionStart() == null
                && request.getSelectionEnd() != null
                && request.getSelectionEnd().isBefore(selection.getSelectionStart())) {
            log.error("Дата и время конца отбора не может быть в раньше его начала.");
            throw new BadRequestException("Дата и время конца отбора не может быть в раньше его начала.");
        }
    }
}
