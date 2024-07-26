package com.kardoaward.kardo.event.model;

import com.kardoaward.kardo.enums.Field;
import com.kardoaward.kardo.enums.Status;
import com.kardoaward.kardo.event.model.enums.EventProgram;
import com.kardoaward.kardo.offlineCompetition.model.OfflineCompetition;
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
    private OfflineCompetition competition;
    @Column(name = "event_start")
    private LocalDateTime eventStart;
    @Column(name = "event_end")
    private LocalDateTime eventEnd;
    private String location;
    @Enumerated(EnumType.STRING)
    private Status status = UPCOMING;
    @ElementCollection
    @CollectionTable(name="event_programs",
            joinColumns=@JoinColumn(name="event_id"))
    @Column(name="program")
    @Enumerated(EnumType.STRING)
/*  ToDo
     После добавления контроллера проверить сохраняются/возвращаются ли значения в этом поле.
 */
    private List<EventProgram> programs;
    @ElementCollection
    @CollectionTable(name="event_fields",
            joinColumns=@JoinColumn(name="event_id"))
    @Column(name="field")
    @Enumerated(EnumType.STRING)
/*  ToDo
     После добавления контроллера проверить сохраняются/возвращаются ли значения в этом поле.
 */
    private List<Field> fields;
    private String logo;
    @Column(name="is_main_event")
    private Boolean isMainEvent;
    private String description;
}
