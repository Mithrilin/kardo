package com.kardoaward.kardo.selection.service.helper;

import com.kardoaward.kardo.exception.NotFoundException;
import com.kardoaward.kardo.selection.model.Selection;
import com.kardoaward.kardo.selection.repository.SelectionRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class SelectionValidationHelper {

    private final SelectionRepository selectionRepository;


}
