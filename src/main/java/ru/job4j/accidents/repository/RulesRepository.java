package ru.job4j.accidents.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.accidents.model.Rule;

import java.util.*;

public interface RulesRepository extends CrudRepository<Rule, Integer> {

    List<Rule> findAll();
}