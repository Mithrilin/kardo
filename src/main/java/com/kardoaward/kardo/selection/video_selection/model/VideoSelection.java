package com.kardoaward.kardo.selection.video_selection.model;

import com.kardoaward.kardo.selection.model.Selection;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "video_selections")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class VideoSelection extends Selection {

    private String hashtag;
    private String description;
}
