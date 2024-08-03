package com.kardoaward.kardo.video_clip.repository;

import com.kardoaward.kardo.video_clip.model.VideoClip;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface VideoClipRepository extends JpaRepository<VideoClip, Long> {

    @Query(value =
            "SELECT * " +
            "FROM video_clips AS vc " +
            "WHERE vc.id IN (SELECT vch.video_clip_id " +
                            "FROM video_clip_hashtags AS vch " +
                            "WHERE vch.hashtag = :hashtag)", nativeQuery = true)
    Page<VideoClip> findAllByHashtag(String hashtag, PageRequest pageRequest);
}
