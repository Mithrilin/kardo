package com.kardoaward.kardo.spectator_request.selection_spectator_request.model;

import com.kardoaward.kardo.selection.offline_selection.model.OfflineSelection;
import com.kardoaward.kardo.spectator_request.model.SpectatorRequest;
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
@Table(name = "selection_spectator_requests")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class SelectionSpectatorRequest extends SpectatorRequest {

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    @JoinColumn(name = "selection_id")
    private OfflineSelection selection;
}
