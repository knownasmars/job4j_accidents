package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.service.HbmAccidentService;
import ru.job4j.accidents.service.SpringDataAccidentService;
import ru.job4j.accidents.service.SpringDataAccidentTypeService;
import ru.job4j.accidents.service.SpringDataRuleService;

import javax.servlet.http.HttpServletRequest;

@Controller
@AllArgsConstructor
public class AccidentController {

    private final SpringDataAccidentService accidentService;
    private final SpringDataAccidentTypeService typeService;
    private final SpringDataRuleService ruleService;

    @GetMapping("/saveAccident")
    public String viewCreateAccident(Model model) {
        model.addAttribute("types", typeService.getAccidentTypes());
        model.addAttribute("rules", ruleService.getRules());
        return "createAccident";
    }

    @PostMapping("/saveAccident")
    public String save(Model model, @ModelAttribute Accident accident, HttpServletRequest req) {
        accident.setRules(ruleService.makeRules(req.getParameterValues("rIds")));
        accidentService.create(accident);
        return "redirect:/index";
    }

    @GetMapping("/{id}")
    public String edit(Model model, @PathVariable int id) {
        var accident = accidentService.findById(id);
        if (accident.isEmpty()) {
            model.addAttribute("message", "Accident not found.");
            return "errors/404";
        }
        model.addAttribute("accident", accident.get());
        model.addAttribute("types", typeService.getAccidentTypes());
        model.addAttribute("rules", ruleService.getRules());
        return "editAccident";
    }

    @PostMapping("/updateAccident")
    public String update(@ModelAttribute Accident accident, HttpServletRequest req) {
        accident.setRules(ruleService.makeRules(req.getParameterValues("rIds")));
        accidentService.update(accident);
        return "redirect:/index";
    }
}