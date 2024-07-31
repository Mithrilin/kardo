package com.kardoaward.kardo.online_competition.service.helper;

import com.kardoaward.kardo.exception.NotFoundException;
import com.kardoaward.kardo.online_competition.model.OnlineCompetition;
import com.kardoaward.kardo.online_competition.repository.OnlineCompetitionRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class OnlineCompetitionValidationHelper {

    private final OnlineCompetitionRepository repository;


}
