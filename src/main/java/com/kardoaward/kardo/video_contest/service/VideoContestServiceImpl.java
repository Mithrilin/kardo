package com.kardoaward.kardo.video_contest.service;

import com.kardoaward.kardo.video_contest.mapper.VideoContestMapper;
import com.kardoaward.kardo.video_contest.model.VideoContest;
import com.kardoaward.kardo.video_contest.model.dto.NewVideoContestRequest;
import com.kardoaward.kardo.video_contest.model.dto.VideoContestDto;
import com.kardoaward.kardo.video_contest.model.dto.UpdateVideoContestRequest;
import com.kardoaward.kardo.video_contest.repository.VideoContestRepository;
import com.kardoaward.kardo.video_contest.service.helper.VideoContestValidationHelper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class VideoContestServiceImpl implements VideoContestService {

    private final VideoContestRepository repository;

    private final VideoContestMapper mapper;

    private final VideoContestValidationHelper videoHelper;

    @Override
    @Transactional
    public VideoContestDto createVideoContest(NewVideoContestRequest newContest) {
        VideoContest contest = mapper.newVideoContestRequestToVideoContest(newContest);
        VideoContest returnedContest = repository.save(contest);
        VideoContestDto contestDto = mapper.videoContestToVideoContestDto(returnedContest);
        log.info("Видео-конкурс с ID = {} создан.", returnedContest.getId());
        return contestDto;
    }

    @Override
    @Transactional
    public void deleteVideoContest(Long contestId) {
        videoHelper.isVideoContestPresent(contestId);
        repository.deleteById(contestId);
        log.info("Видео-конкурс с ID {} удалён.", contestId);
    }

    @Override
    public VideoContestDto getVideoContestById(Long contestId) {
        VideoContest contest = videoHelper.isVideoContestPresent(contestId);
        VideoContestDto contestDto = mapper.videoContestToVideoContestDto(contest);
        log.info("Видео-конкурс с ИД {} возвращён.", contestId);
        return contestDto;
    }

    @Override
    public List<VideoContestDto> getVideoContests(int from, int size) {
        int page = from / size;
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        Page<VideoContest> contestsPage = repository.findAll(pageRequest);

        if (contestsPage.isEmpty()) {
            log.info("Не нашлось видео-конкурсов по заданным параметрам.");
            return new ArrayList<>();
        }

        List<VideoContest> contests = contestsPage.getContent();
        List<VideoContestDto> contestDtos = mapper.videoContestListToVideoContestDtoList(contests);
        log.info("Список видео-конкурсов с номера {} размером {} возвращён.", from, contestDtos.size());
        return contestDtos;
    }

    @Override
    @Transactional
    public VideoContestDto updateVideoContest(Long contestId, UpdateVideoContestRequest request) {
        VideoContest contest = videoHelper.isVideoContestPresent(contestId);
        videoHelper.isUpdateVideoContestDateValid(contest, request);
        mapper.updateVideoContest(request, contest);
        VideoContest updatedContest = repository.save(contest);
        VideoContestDto contestDto = mapper.videoContestToVideoContestDto(updatedContest);
        log.info("Видео-конкурс с ID {} обновлён.", contestId);
        return contestDto;
    }
}