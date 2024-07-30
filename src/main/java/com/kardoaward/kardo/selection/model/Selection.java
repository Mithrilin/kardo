package com.kardoaward.kardo.selection.model;

import com.kardoaward.kardo.enums.Field;
import com.kardoaward.kardo.enums.Status;
import com.kardoaward.kardo.offline_competition.model.OfflineCompetition;
import com.kardoaward.kardo.selection.model.enums.SelectionType;
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

import java.time.LocalDate;
import java.util.List;

import static com.kardoaward.kardo.enums.Status.UPCOMING;

@Entity
@Table(name = "selections")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Selection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String hashtag;
    @Enumerated(EnumType.STRING)
    @Column(name = "selection_type")
    private SelectionType selectionType;
    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    @JoinColumn(name = "competition_id")
    private OfflineCompetition competition;
    @Column(name = "selection_start")
    private LocalDate selectionStart;
    @Column(name = "selection_end")
    private LocalDate selectionEnd;
    @Enumerated(EnumType.STRING)
    private Status status = UPCOMING;
    @ElementCollection
    @CollectionTable(name="selection_fields",
            joinColumns=@JoinColumn(name="selection_id"))
    @Column(name="field")
    @Enumerated(EnumType.STRING)
    private List<Field> fields;
    private String location;
}
