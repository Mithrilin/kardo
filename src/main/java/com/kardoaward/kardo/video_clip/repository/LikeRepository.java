package com.kardoaward.kardo.video_clip.repository;

import com.kardoaward.kardo.video_clip.model.like.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {
}
