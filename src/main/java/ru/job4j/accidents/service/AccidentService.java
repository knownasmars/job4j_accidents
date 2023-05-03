package ru.job4j.accidents.service;

import ru.job4j.accidents.model.*;

import java.util.*;

public interface AccidentService {

    Accident save(Accident accident);

    void update(Accident accident);

    List<Accident> findAll();

    Optional<Accident> findById(int id);

    List<AccidentType> getAccidentTypes();

    List<Rule> getRules();

    Set<Rule> makeRules(String[] ids);
}