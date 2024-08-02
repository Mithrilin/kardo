package com.kardoaward.kardo.selection.offline_selection.service.helper;

import com.kardoaward.kardo.exception.BadRequestException;
import com.kardoaward.kardo.exception.NotFoundException;
import com.kardoaward.kardo.selection.offline_selection.model.OfflineSelection;
import com.kardoaward.kardo.selection.offline_selection.model.dto.UpdateOfflineSelectionRequest;
import com.kardoaward.kardo.selection.offline_selection.repository.OfflineSelectionRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class OfflineSelectionValidationHelper {

    private final OfflineSelectionRepository offlineSelectionRepository;

    public OfflineSelection isOfflineSelectionPresent(Long selectionId) {
        Optional<OfflineSelection> optionalOfflineSelection = offlineSelectionRepository.findById(selectionId);

        if (optionalOfflineSelection.isEmpty()) {
            log.error("Оффлайн-отбор с ИД {} отсутствует в БД.", selectionId);
            throw new NotFoundException(String.format("Оффлайн-отбор с ИД %d отсутствует в БД.", selectionId));
        }

        return optionalOfflineSelection.get();
    }

    public void isUpdateOfflineSelectionDateValid(OfflineSelection offlineSelection,
                                                  UpdateOfflineSelectionRequest request) {
        if (request.getSelectionStart() != null
                && request.getSelectionEnd() != null
                && request.getSelectionEnd().isBefore(request.getSelectionStart())) {
            log.error("Дата начала оффлайн-отбора не может быть после его конца.");
            throw new BadRequestException("Дата начала оффлайн-отбора не может быть после его конца.");
        }

        if (request.getSelectionStart() != null
                && request.getSelectionEnd() == null
                && request.getSelectionStart().isAfter(offlineSelection.getSelectionEnd())) {
            log.error("Дата начала оффлайн-отбора не может быть после его конца.");
            throw new BadRequestException("Дата начала оффлайн-отбора не может быть после его конца.");
        }

        if (request.getSelectionStart() == null
                && request.getSelectionEnd() != null
                && request.getSelectionEnd().isBefore(offlineSelection.getSelectionStart())) {
            log.error("Дата конца оффлайн-отбора не может быть раньше его начала.");
            throw new BadRequestException("Дата конца оффлайн-отбора не может быть раньше его начала.");
        }
    }
}
