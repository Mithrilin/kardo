package com.kardoaward.kardo.video_clip.model;

import com.kardoaward.kardo.user.model.User;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Formula;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "video_clips")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class VideoClip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "publication_time")
    private LocalDateTime publicationTime = LocalDateTime.now();
    @ElementCollection
    @CollectionTable(name="video_clip_hashtags",
            joinColumns=@JoinColumn(name="video_clip_id"))
    @Column(name="hashtag")
    private Set<String> hashtags;
    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    @JoinColumn(name = "creator_id")
    private User creator;
    @Formula("(SELECT COUNT(l.id) " +
              "FROM likes AS l " +
              "WHERE l.video_clip_id = id)")
    private Integer likesCount = 0;
    @Column(name="video_link")
    private String videoLink;
}
