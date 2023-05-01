package ru.job4j.accidents.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class AccidentMem {

    private final AtomicInteger nextId = new AtomicInteger(1);

    private Map<Integer, Accident> accidents = new ConcurrentHashMap<>();

    private AccidentMem() {
        save(new Accident(0, "test", "test", "test"));
        save(new Accident(0, "test1", "test1", "test1"));
        save(new Accident(0, "test2", "test2", "test2"));
    }

    public Accident save(Accident accident) {
        accident.setId(nextId.incrementAndGet());
        accidents.put(accident.getId(), accident);
        return accident;
    }

    public Optional<Accident> findById(int id) {
        return Optional.ofNullable(accidents.get(id));
    }

    public boolean update(Accident accident) {
        return accidents.computeIfPresent(accident.getId(),
                (id, oldAccident) ->
                        new Accident(oldAccident.getId(),
                                accident.getName(),
                                accident.getText(),
                                accident.getAddress())) != null;
    }

    public Collection<Accident> findAll() {
        return accidents.values();
    }
}
