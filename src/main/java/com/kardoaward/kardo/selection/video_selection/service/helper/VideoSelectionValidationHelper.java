package com.kardoaward.kardo.selection.video_selection.service.helper;

import com.kardoaward.kardo.exception.BadRequestException;
import com.kardoaward.kardo.exception.NotFoundException;
import com.kardoaward.kardo.selection.model.dto.UpdateSelectionRequest;
import com.kardoaward.kardo.selection.video_selection.model.VideoSelection;
import com.kardoaward.kardo.selection.video_selection.repository.VideoSelectionRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class VideoSelectionValidationHelper {

    private final VideoSelectionRepository videoSelectionRepository;

    public VideoSelection isVideoSelectionPresent(Long selectionId) {
        Optional<VideoSelection> optionalVideoSelection = videoSelectionRepository.findById(selectionId);

        if (optionalVideoSelection.isEmpty()) {
            log.error("Видео-отбор с ИД {} отсутствует в БД.", selectionId);
            throw new NotFoundException(String.format("Видео-отбор с ИД %d отсутствует в БД.", selectionId));
        }

        return optionalVideoSelection.get();
    }

    public void isUpdateSelectionDateValid(VideoSelection selection, UpdateSelectionRequest request) {
        if (request.getSelectionStart() != null
                && request.getSelectionEnd() != null
                && request.getSelectionEnd().isBefore(request.getSelectionStart())) {
            log.error("Дата и время начала видео-отбора не может быть после его конца.");
            throw new BadRequestException("Дата и время начала видео-отбора не может быть после его конца.");
        }

        if (request.getSelectionStart() != null
                && request.getSelectionEnd() == null
                && request.getSelectionStart().isAfter(selection.getSelectionEnd())) {
            log.error("Дата и время начала видео-отбора не может быть после его конца.");
            throw new BadRequestException("Дата и время начала видео-отбора не может быть после его конца.");
        }

        if (request.getSelectionStart() == null
                && request.getSelectionEnd() != null
                && request.getSelectionEnd().isBefore(selection.getSelectionStart())) {
            log.error("Дата и время конца видео-отбора не может быть раньше его начала.");
            throw new BadRequestException("Дата и время конца видео-отбора не может быть раньше его начала.");
        }
    }
}
