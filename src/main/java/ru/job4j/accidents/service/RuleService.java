package ru.job4j.accidents.service;

import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.RuleMem;

import java.util.Optional;
import java.util.Set;

@Service
public class RuleService {

    private final RuleMem ruleMem;

    public RuleService(RuleMem ruleMem) {
        this.ruleMem = ruleMem;
    }

    public Set<Rule> getAllRules() {
        return ruleMem.getAllRules();
    }

    public Optional<Rule> findById(int id) {
        return ruleMem.findById(id);
    }
}
