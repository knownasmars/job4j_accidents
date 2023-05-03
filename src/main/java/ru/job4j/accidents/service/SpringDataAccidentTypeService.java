package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.*;
import ru.job4j.accidents.repository.AccidentTypeRepository;

import java.util.*;

@Service
@AllArgsConstructor
public class SpringDataAccidentTypeService {

    private final AccidentTypeRepository accidentTypeRepository;

    public List<AccidentType> getAccidentTypes() {
        return accidentTypeRepository.findAll();
    }
}