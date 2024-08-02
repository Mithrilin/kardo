package com.kardoaward.kardo.event.service.specification;

import com.kardoaward.kardo.enums.Field;
import com.kardoaward.kardo.event.model.Event;
import com.kardoaward.kardo.event.model.enums.EventProgram;
import com.kardoaward.kardo.event.model.params.EventRequestParams;
import jakarta.persistence.criteria.Join;
import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@UtilityClass
public class EventSpecifications {

    public static List<Specification<Event>> searchEventFilterToSpecifications(EventRequestParams params) {
        List<Specification<Event>> specifications = new ArrayList<>();
        specifications.add(params.getOfflineCompetitionId() == null ? null : offlineCompetitionIdEqual(
                params.getOfflineCompetitionId()));
        specifications.add(params.getDay() == null ? null : dayEqual(params.getDay()));
        specifications.add(params.getProgram() == null ? null : programEqual(params.getProgram()));
        specifications.add(params.getField() == null ? null : fieldEqual(params.getField()));
        return specifications.stream().filter(Objects::nonNull).collect(Collectors.toList());
    }

    private static Specification<Event> offlineCompetitionIdEqual(Long values) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("competition").get("id"), values);
    }

    private static Specification<Event> dayEqual(LocalDate values) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("eventStart").get("date"), values);
    }

    private static Specification<Event> programEqual(EventProgram values) {
        return (root, query, criteriaBuilder) -> {
            Join<EventProgram, Event> programEventJoin = root.join("programs");
            return criteriaBuilder.equal(programEventJoin.get("program"), values);
        };
    }

    private static Specification<Event> fieldEqual(Field values) {
        return (root, query, criteriaBuilder) -> {
            Join<Field, Event> fieldEventJoin = root.join("fields");
            return criteriaBuilder.equal(fieldEventJoin.get("field"), values);
        };
    }
}
