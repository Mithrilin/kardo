package com.kardoaward.kardo.participationRequest.model;

import com.kardoaward.kardo.enums.Field;
import com.kardoaward.kardo.enums.RequestStatus;
import com.kardoaward.kardo.selection.model.Selection;
import com.kardoaward.kardo.user.model.User;
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

@Entity
@Table(name = "participation_requests")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ParticipationRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "creation_time")
    private LocalDateTime creationTime;
    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    @JoinColumn(name = "selection_id")
    private Selection selection;
    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    @JoinColumn(name = "requester_id")
    private User requester;
    @Enumerated(EnumType.STRING)
    private RequestStatus status;
    @ElementCollection
    @CollectionTable(name="participation_request_fields",
            joinColumns=@JoinColumn(name="participation_request_id"))
    @Column(name="field")
    @Enumerated(EnumType.STRING)
/*  ToDo
     После добавления контроллера проверить сохраняются/возвращаются ли значения в этом поле.
 */
    private List<Field> fields;
}
