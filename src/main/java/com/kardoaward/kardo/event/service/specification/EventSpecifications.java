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
        specifications.add(params.getGrandCompetitionId() == null ? null : grandCompetitionIdEqual(
                params.getGrandCompetitionId()));
        specifications.add(params.getDay() == null ? null : dayEqual(params.getDay()));
        specifications.add(params.getProgram() == null ? null : programEqual(params.getProgram()));
        specifications.add(params.getField() == null ? null : fieldEqual(params.getField()));
        return specifications.stream().filter(Objects::nonNull).collect(Collectors.toList());
    }

    private static Specification<Event> grandCompetitionIdEqual(Long value) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("competition").get("id"), value);
    }

    private static Specification<Event> dayEqual(LocalDate value) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("eventStart").as(LocalDate.class),
                value);
    }

    private static Specification<Event> programEqual(EventProgram value) {
        return (root, query, criteriaBuilder) -> {
            Join<EventProgram, Event> programEventJoin = root.join("programs");
            return criteriaBuilder.equal(programEventJoin, value);
        };

    }

    private static Specification<Event> fieldEqual(Field value) {
        return (root, query, criteriaBuilder) -> {
            Join<Field, Event> fieldEventJoin = root.join("fields");
            return criteriaBuilder.equal(fieldEventJoin, value);
        };
    }
}
