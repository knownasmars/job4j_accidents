package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.*;

import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.AccidentMem;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AccidentService {

    private final AccidentMem accidentMem;

    private final AccidentTypeService typeService;

    public Accident save(Accident accident) {
        Optional<AccidentType> opt = typeService.findTypeById(accident.getType().getId());
        if (opt.isEmpty()) {
            throw new NoSuchElementException("The accident type is not found");
        }
        accident.setType(opt.get());
        return accidentMem.save(accident);
    }

    public Optional<Accident> findById(int id) {
        return accidentMem.findById(id);
    }

    public boolean update(Accident accident) {
        Optional<AccidentType> accidentTypeOptional =
                typeService.findTypeById(accident.getType().getId());
        if (accidentTypeOptional.isEmpty()) {
            throw new NoSuchElementException("The accident type is not found");
        }
        accident.setType(accidentTypeOptional.get());
        return accidentMem.update(accident);
    }

    public List<Accident> findAll() {
        return accidentMem.findAll();
    }
}
