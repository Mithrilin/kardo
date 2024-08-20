package com.kardoaward.kardo.event.model;

import com.kardoaward.kardo.enums.Field;
import com.kardoaward.kardo.enums.Status;
import com.kardoaward.kardo.event.enums.EventProgram;
import com.kardoaward.kardo.grand_competition.model.GrandCompetition;
import com.kardoaward.kardo.media_file.model.MediaFile;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
import java.util.List;

import static com.kardoaward.kardo.enums.Status.UPCOMING;

@Entity
@Table(name = "events")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    @JoinColumn(name = "competition_id")
    private GrandCompetition competition;
    @Column(name = "event_start")
    private LocalDateTime eventStart;
    @Column(name = "event_end")
    private LocalDateTime eventEnd;
    private String location;
    @Enumerated(EnumType.STRING)
    private Status status = UPCOMING;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name="event_programs",
            joinColumns=@JoinColumn(name="event_id"))
    @Column(name="program")
    @Enumerated(EnumType.STRING)
    private List<EventProgram> programs;
    @ElementCollection
    @CollectionTable(name="event_fields",
            joinColumns=@JoinColumn(name="event_id"))
    @Column(name="field")
    @Enumerated(EnumType.STRING)
    private List<Field> fields;
    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    @JoinColumn(name = "logo_id")
    private MediaFile logo;
    @Column(name="is_main_event")
    private Boolean isMainEvent;
    private String description;
}
