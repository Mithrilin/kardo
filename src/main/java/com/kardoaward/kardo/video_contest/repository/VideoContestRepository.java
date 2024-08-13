package com.kardoaward.kardo.video_contest.repository;

import com.kardoaward.kardo.video_contest.model.VideoContest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoContestRepository extends JpaRepository<VideoContest, Long> {
}
