package com.kardoaward.kardo.video_clip.model;

import com.kardoaward.kardo.user.model.User;
import jakarta.persistence.*;
import lombok.*;

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
    @CollectionTable(name = "video_clip_hashtags",
            joinColumns = @JoinColumn(name = "video_clip_id"))
    @Column(name = "hashtag")
/*  ToDo
     После добавления контроллера проверить сохраняются/возвращаются ли значения в этом поле.
 */
    private Set<String> hashtags;
    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    @JoinColumn(name = "creator_id")
    private User creator;
    @Transient
    private Integer likesCount = 0;
    @Column(name = "video_link")
    private String videoLink;
}
