package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repository.AccidentRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SpringDataAccidentService {

    private final AccidentRepository accidentRepository;

    public void create(Accident accident) {
        accidentRepository.save(accident);
    }

    public void update(Accident accident) {
        accidentRepository.save(accident);
    }

    public List<Accident> getAll() {
        return (List<Accident>) accidentRepository.findAll();
    }

    public Optional<Accident> findById(int id) {
        return accidentRepository.findById(id);
    }
}