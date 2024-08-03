package com.kardoaward.kardo.spectator_request.event_spectator_request.model;

import com.kardoaward.kardo.event.model.Event;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "event_spectator_requests")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class EventSpectatorRequest {

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    @JoinColumn(name = "event_id")
    private Event event;
}
