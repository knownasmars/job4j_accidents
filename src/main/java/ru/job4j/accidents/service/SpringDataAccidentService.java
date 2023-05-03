package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.*;
import ru.job4j.accidents.repository.AccidentRepository;

import java.util.*;

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

    public List<Accident> findAll() {
        return accidentRepository.findAll();
    }

    public Optional<Accident> findById(int id) {
        return accidentRepository.findById(id);
    }
}