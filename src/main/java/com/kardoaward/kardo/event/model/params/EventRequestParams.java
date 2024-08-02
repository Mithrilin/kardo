package com.kardoaward.kardo.event.model.params;

import com.kardoaward.kardo.enums.Field;
import com.kardoaward.kardo.event.model.enums.EventProgram;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;

@Data
public class EventRequestParams {

    private Long offlineCompetitionId;
    private LocalDate day;
    private EventProgram program;
    private Field field;
    private Integer from;
    private Integer size;
    private Integer page;
    private Sort sort;
    private PageRequest pageRequest;

    public EventRequestParams(Long offlineCompetitionId,
                              LocalDate day,
                              EventProgram program,
                              Field field,
                              Integer from,
                              Integer size) {
        this.offlineCompetitionId = offlineCompetitionId;
        this.day = day;
        this.program = program;
        this.field = field;
        this.from = from;
        this.size = size;
        this.page = from / size;
        this.sort = Sort.by(Sort.Direction.ASC, "id");
        this.pageRequest = PageRequest.of(page, size, sort);
    }
}
