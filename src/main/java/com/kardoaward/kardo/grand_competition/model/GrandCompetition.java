package com.kardoaward.kardo.grand_competition.model;

import com.kardoaward.kardo.enums.Status;
import com.kardoaward.kardo.enums.Field;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
@Table(name = "grand_competitions")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class GrandCompetition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String hashtag;
    @Column(name = "competition_start")
    private LocalDate competitionStart;
    @Column(name = "competition_end")
    private LocalDate competitionEnd;
    @Enumerated(EnumType.STRING)
    private Status status = UPCOMING;
    private String location;
    @ElementCollection
    @CollectionTable(name="grand_competition_fields",
            joinColumns=@JoinColumn(name="grand_competition_id"))
    @Column(name="field")
    @Enumerated(EnumType.STRING)
    private List<Field> fields;
    private String description;
}
