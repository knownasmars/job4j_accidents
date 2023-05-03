package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.*;
import ru.job4j.accidents.repository.*;

import java.util.*;

@Service
@AllArgsConstructor
public class SpringDataRuleService {

    private final RulesRepository rulesRepository;

    public List<Rule> getRules() {
        return rulesRepository.findAll();
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