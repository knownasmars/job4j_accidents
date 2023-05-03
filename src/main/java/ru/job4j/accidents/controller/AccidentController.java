package ru.job4j.accidents.controller;

import lombok.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;
import ru.job4j.accidents.service.*;
import ru.job4j.accidents.model.*;

import javax.servlet.http.*;

@Controller
@RequestMapping("/accidents")
@AllArgsConstructor
public class AccidentController {

    private final SpringDataAccidentService accidentService;
    private final SpringDataAccidentTypeService typeService;
    private final SpringDataRuleService ruleService;

    @GetMapping("/all")
    public String getAll(Model model) {
        model.addAttribute("accidents", accidentService.findAll());
        return "accidents/all";
    }

    @GetMapping("/createAccident")
    public String viewCreateAccident(Model model) {
        model.addAttribute("types", typeService.getAccidentTypes());
        model.addAttribute("rules", ruleService.getRules());
        return "accidents/createAccident";
    }

    @PostMapping("/saveAccident")
    public String save(@ModelAttribute Accident accident, HttpServletRequest req) {
        accident.setRules(ruleService.makeRules(req.getParameterValues("rIds")));
        accidentService.create(accident);
        return "redirect:/accidents/all";
    }

    @GetMapping("/formUpdateAccident")
    public String edit(@RequestParam("id") int id, Model model) {
        var accident = accidentService.findById(id);
        if (accident.isEmpty()) {
            model.addAttribute("message", "Инцидент не найден.");
            return "errors/404";
        }
        model.addAttribute("accident", accident.get());
        model.addAttribute("types", typeService.getAccidentTypes());
        model.addAttribute("rules", ruleService.getRules());
        return "accidents/editAccident";
    }

    @PostMapping("/updateAccident")
    public String update(@ModelAttribute Accident accident, HttpServletRequest req) {
        accident.setRules(ruleService.makeRules(req.getParameterValues("rIds")));
        accidentService.update(accident);
        return "redirect:/accidents/all";
    }

}