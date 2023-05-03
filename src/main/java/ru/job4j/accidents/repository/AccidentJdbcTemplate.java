package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.*;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.*;

import java.sql.*;
import java.util.*;

@Repository
@AllArgsConstructor
public class AccidentJdbcTemplate {

    private final JdbcTemplate jdbc;

    public Accident save(Accident accident) {
        final String INSERT_SQL = "insert into accidents (name, description, address, accident_type_id) "
                + "values (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(con -> {
            PreparedStatement ps = con.prepareStatement(INSERT_SQL, new String[] {"id"});
            ps.setString(1, accident.getName());
            ps.setString(2, accident.getText());
            ps.setString(3, accident.getAddress());
            ps.setInt(4, accident.getType().getId());
            return ps;
        }, keyHolder);
        accident.setId(keyHolder.getKey().intValue());
        for (Rule rule : accident.getRules()) {
            jdbc.update("insert into accident_rules (accident_id, rule_id) "
                            + "values (?, ?)",
                    accident.getId(),
                    rule.getId());
        }
        return accident;
    }

    public void update(Accident accident) {
        int id = accident.getId();
        jdbc.update("update accidents set name = ?, description = ?, address = ?, accident_type_id = ?"
                        + " where id = ?",
                accident.getName(),
                accident.getText(),
                accident.getAddress(),
                accident.getType().getId(),
                id);
        jdbc.update("delete from accident_rules where accident_id = ?", id);
        for (Rule rule : accident.getRules()) {
            jdbc.update("insert into accident_rules (accident_id, rule_id) "
                            + "values (?, ?)",
                    id,
                    rule.getId());
        }
    }

    public List<Accident> getAll() {
        return jdbc.query("select id, name, description, address from accidents",
                (rs, row) -> {
                    Accident accident = new Accident();
                    accident.setId(rs.getInt("id"));
                    accident.setName(rs.getString("name"));
                    accident.setText(rs.getString("description"));
                    accident.setAddress(rs.getString("address"));
                    accident.setType(new AccidentType());
                    accident.setRules(Set.of(new Rule()));
                    return accident;
                });
    }

    public Optional<Accident> findById(int id) {
        Accident res = jdbc.queryForObject("select id, name, description, address, accident_type_id "
                        + "from accidents where id = ?",
                (rs, row) -> {
                    Accident accident = new Accident();
                    accident.setId(rs.getInt("id"));
                    accident.setName(rs.getString("name"));
                    accident.setText(rs.getString("description"));
                    accident.setAddress(rs.getString("address"));
                    accident.setType(new AccidentType(rs.getInt("accident_type_id"), ""));
                    return accident;
                }, id);
        List<Rule> rules = jdbc.query("select rule_id from accident_rules where accident_id = ?",
                (rs, row) -> {
                    Rule rule = new Rule();
                    rule.setId(rs.getInt("rule_id"));
                    return rule;
                }, id);
        res.setRules(new HashSet<>(rules));
        return Optional.ofNullable(res);
    }

    public Collection<AccidentType> getAccidentTypes() {
        return jdbc.query("select id, name from accident_type",
                (rs, row) -> {
                    AccidentType accidentType = new AccidentType();
                    accidentType.setId(rs.getInt("id"));
                    accidentType.setName(rs.getString("name"));
                    return accidentType;
                });
    }

    public Collection<Rule> getRules() {
        return jdbc.query("select id, name from rule",
                (rs, row) -> {
                    Rule rule = new Rule();
                    rule.setId(rs.getInt("id"));
                    rule.setName(rs.getString("name"));
                    return rule;
                });
    }
}