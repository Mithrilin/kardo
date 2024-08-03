package com.kardoaward.kardo.video_clip.repository;

import com.kardoaward.kardo.video_clip.model.VideoClip;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoClipRepository extends JpaRepository<VideoClip, Long> {
}
