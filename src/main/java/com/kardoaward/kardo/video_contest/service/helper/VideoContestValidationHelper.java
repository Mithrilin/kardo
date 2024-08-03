package com.kardoaward.kardo.video_contest.service.helper;

import com.kardoaward.kardo.exception.BadRequestException;
import com.kardoaward.kardo.exception.NotFoundException;
import com.kardoaward.kardo.video_contest.model.VideoContest;
import com.kardoaward.kardo.video_contest.model.dto.NewVideoContestRequest;
import com.kardoaward.kardo.video_contest.repository.VideoContestRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class VideoContestValidationHelper {

    private final VideoContestRepository repository;

    public VideoContest isVideoContestPresent(Long videoContestId) {
        Optional<VideoContest> optionalVideoContest = repository.findById(videoContestId);

        if (optionalVideoContest.isEmpty()) {
            log.error("Видео-конкурс с ИД {} отсутствует в БД.", videoContestId);
            throw new NotFoundException(String.format("Видео-конкурс с ИД %d отсутствует в БД.", videoContestId));
        }

        return optionalVideoContest.get();
    }

    public void isNewVideoContestDateValid(NewVideoContestRequest newVideoContest) {
        if (newVideoContest.getContestEnd().isBefore(newVideoContest.getContestStart())) {
            log.error("Дата начала видео-конкурса не может быть после его конца.");
            throw new BadRequestException("Дата начала видео-конкурса не может быть после его конца.");
        }
    }
}
