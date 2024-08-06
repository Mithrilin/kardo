package com.kardoaward.kardo.video_clip.model.like;

import com.kardoaward.kardo.user.model.User;
import com.kardoaward.kardo.video_clip.model.VideoClip;
import jakarta.persistence.Column;
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

import java.time.LocalDateTime;

@Entity
@Table(name = "likes")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "addition_time")
    private LocalDateTime additionTime = LocalDateTime.now();
    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    @JoinColumn(name = "creator_id")
    private User creator;
    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    @JoinColumn(name = "video_clip_id")
    private VideoClip videoClip;
}
