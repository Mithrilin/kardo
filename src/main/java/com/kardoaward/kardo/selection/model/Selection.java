package com.kardoaward.kardo.selection.model;

import com.kardoaward.kardo.enums.Status;
import com.kardoaward.kardo.grand_competition.model.GrandCompetition;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

import static com.kardoaward.kardo.enums.Status.UPCOMING;

@Data
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public class Selection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    @JoinColumn(name = "competition_id")
    private GrandCompetition competition;
    @Column(name = "selection_start")
    private LocalDate selectionStart;
    @Column(name = "selection_end")
    private LocalDate selectionEnd;
    @Enumerated(EnumType.STRING)
    private Status status = UPCOMING;
}
