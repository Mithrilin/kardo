package com.kardoaward.kardo.selection.repository;

import com.kardoaward.kardo.selection.model.Selection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SelectionRepository extends JpaRepository<Selection, Long> {

    /* ToDo
        Написать SQL запрос.
     */
    Page<Selection> findAllByRequestorId(Long requestorId, PageRequest pageRequest);
}
