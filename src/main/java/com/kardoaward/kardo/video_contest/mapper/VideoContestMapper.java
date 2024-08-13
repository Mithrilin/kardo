package com.kardoaward.kardo.video_contest.mapper;

import com.kardoaward.kardo.video_contest.model.VideoContest;
import com.kardoaward.kardo.video_contest.model.dto.NewVideoContestRequest;
import com.kardoaward.kardo.video_contest.model.dto.VideoContestDto;
import com.kardoaward.kardo.video_contest.model.dto.UpdateVideoContestRequest;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring")
public interface VideoContestMapper {

    VideoContest newVideoContestRequestToVideoContest(NewVideoContestRequest newContest);

    VideoContestDto videoContestToVideoContestDto(VideoContest contest);

    List<VideoContestDto> videoContestListToVideoContestDtoList(List<VideoContest> contests);

    @BeanMapping(nullValuePropertyMappingStrategy = IGNORE)
    void updateVideoContest(UpdateVideoContestRequest request, @MappingTarget VideoContest contest);
}
