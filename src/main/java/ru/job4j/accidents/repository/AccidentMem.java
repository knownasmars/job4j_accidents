package ru.job4j.accidents.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class AccidentMem {

    private final AtomicInteger nextId = new AtomicInteger(1);

    private Map<Integer, Accident> accidents = new ConcurrentHashMap<>();

    private AccidentMem() {
    }

    public boolean save(Accident accident) {
        accident.setId(nextId.incrementAndGet());
        return accidents.putIfAbsent(accident.getId(), accident) == null;
    }

    public Optional<Accident> findById(int id) {
        return Optional.ofNullable(accidents.get(id));
    }

    public boolean update(Accident accident) {
        return accidents.replace(accident.getId(), accident) != null;
    }

    public List<Accident> findAll() {
        return new ArrayList<>(accidents.values());
    }
}
