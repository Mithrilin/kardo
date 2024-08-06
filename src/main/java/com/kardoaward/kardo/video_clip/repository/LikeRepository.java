package com.kardoaward.kardo.video_clip.repository;

import com.kardoaward.kardo.video_clip.model.like.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {

    Optional<Like> findByCreator_IdAndVideoClip_Id(Long requestorId, Long videoId);
}
