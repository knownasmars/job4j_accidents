package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.*;
import ru.job4j.accidents.repository.AccidentJdbcTemplate;

import java.util.*;

@Service
@AllArgsConstructor
public class JDBCAccidentService {

    private final AccidentJdbcTemplate accidentsRepository;

    public void create(Accident accident) {
        accidentsRepository.save(accident);
    }

    public void update(Accident accident) {
        accidentsRepository.update(accident);
    }

    public Collection<Accident> findAll() {
        return accidentsRepository.getAll();
    }

    public Optional<Accident> findById(int id) {
        return accidentsRepository.findById(id);
    }

    public Collection<AccidentType> getAccidentTypes() {
        return accidentsRepository.getAccidentTypes();
    }

    public Collection<Rule> getRules() {
        return accidentsRepository.getRules();
    }

    public Set<Rule> makeRules(String[] ids) {
        Set<Rule> rules = new HashSet<>();
        for (String id : ids) {
            Rule rule = new Rule();
            rule.setId(Integer.parseInt(id));
            rules.add(rule);
        }
        return rules;
    }
}