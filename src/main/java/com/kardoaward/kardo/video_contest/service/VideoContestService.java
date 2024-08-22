package com.kardoaward.kardo.video_contest.service;

import com.kardoaward.kardo.video_contest.dto.NewVideoContestRequest;
import com.kardoaward.kardo.video_contest.dto.VideoContestDto;
import com.kardoaward.kardo.video_contest.dto.UpdateVideoContestRequest;

import java.util.List;

public interface VideoContestService {

    VideoContestDto createVideoContest(NewVideoContestRequest newContest);

    void deleteVideoContest(Long contestId);

    VideoContestDto getVideoContestById(Long contestId);

    List<VideoContestDto> getVideoContests(int from, int size);

    VideoContestDto updateVideoContest(Long contestId, UpdateVideoContestRequest request);
}