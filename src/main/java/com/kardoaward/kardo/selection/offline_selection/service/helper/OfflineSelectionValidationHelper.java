package com.kardoaward.kardo.selection.offline_selection.service.helper;

import com.kardoaward.kardo.exception.NotFoundException;
import com.kardoaward.kardo.selection.offline_selection.model.OfflineSelection;
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
}
