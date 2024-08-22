package com.kardoaward.kardo.selection.offline_selection.model;

import com.kardoaward.kardo.enums.Field;
import com.kardoaward.kardo.selection.model.Selection;
import com.kardoaward.kardo.selection.offline_selection.enums.SelectionType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "offline_selections")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class OfflineSelection extends Selection {

    @Enumerated(EnumType.STRING)
    @Column(name = "selection_type")
    private SelectionType selectionType;
    @ElementCollection
    @CollectionTable(name="offline_selection_fields",
            joinColumns=@JoinColumn(name="offline_selection_id"))
    @Column(name="field")
    @Enumerated(EnumType.STRING)
    private List<Field> fields;
    private String location;
}
