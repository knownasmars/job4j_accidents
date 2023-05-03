package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.*;
import java.util.*;

@Repository
@AllArgsConstructor
public class AccidentHibernate {

    private final CrudRepository crudRepository;

    public Accident save(Accident accident) {
        crudRepository.run(session -> session.persist(accident));
        return accident;
    }

    public void update(Accident accident) {
        crudRepository.run(session -> session.merge(accident));
    }

    public Optional<Accident> findById(int id) {
        return crudRepository.optional(
                "FROM Accident ac JOIN FETCH ac.rules WHERE ac.id = :fId",
                Accident.class,
                Map.of("fId", id)
        );
    }

    public List<Accident> getAll() {
        return crudRepository.query("FROM Accident", Accident.class);
    }

    public List<AccidentType> getAccidentTypes() {
        return crudRepository.query("FROM AccidentType", AccidentType.class);
    }

    public List<Rule> getRules() {
        return crudRepository.query("FROM Rule", Rule.class);
    }
}