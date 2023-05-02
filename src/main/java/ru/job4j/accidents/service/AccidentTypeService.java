package ru.job4j.accidents.service;

import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.AccidentTypeMem;

import java.util.List;
import java.util.Optional;

@Service
public class AccidentTypeService {

    private final AccidentTypeMem typeMem;

    public AccidentTypeService(AccidentTypeMem typeMem) {
        this.typeMem = typeMem;
    }

    public List<AccidentType> getAllTypes() {
        return typeMem.getAllTypes();
    }

    public Optional<AccidentType> findTypeById(int id) {
        return typeMem.findTypeById(id);
    }
}