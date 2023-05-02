package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.*;

import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.AccidentMem;

import java.util.*;

@Service
@AllArgsConstructor
public class AccidentService {

    private final AccidentMem accidentMem;

    private final AccidentTypeService typeService;

    private final RuleService ruleService;

    public boolean save(Accident accident, int type, String[] ruleIds) {
        Accident forAdd = setTypeAndRules(accident, type, ruleIds);
        return accidentMem.save(forAdd);
    }

    private Accident setTypeAndRules(Accident accident, int typeId, String[] rIds) {
        Optional<AccidentType> accidentTypeOptional = typeService.findTypeById(typeId);
        if (accidentTypeOptional.isEmpty()) {
            throw new NoSuchElementException("The accident type is not found");
        }
        Set<Rule> ruleSet = new LinkedHashSet<>();

        for (String id: rIds) {
            int accidentId = Integer.parseInt(id);
            Optional<Rule> ruleOptional = ruleService.findById(accidentId);
            if (ruleOptional.isEmpty()) {
                throw new NoSuchElementException("The accident rule is not found");
            }
            ruleSet.add(ruleOptional.get());
        }
        accident.setType(accidentTypeOptional.get());
        accidentMem.save(accident);
        accident.setRules(ruleSet);

        return accident;
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
