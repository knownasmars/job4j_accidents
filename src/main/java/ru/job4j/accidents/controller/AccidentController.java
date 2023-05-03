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

import javax.servlet.http.HttpServletRequest;

@Controller
@AllArgsConstructor
public class AccidentController {

    private final HbmAccidentService accidentService;

    @GetMapping("/saveAccident")
    public String viewCreateAccident(Model model) {
        model.addAttribute("types", accidentService.getAccidentTypes());
        model.addAttribute("rules", accidentService.getRules());
        return "createAccident";
    }

    @PostMapping("/saveAccident")
    public String save(Model model, @ModelAttribute Accident accident, HttpServletRequest req) {
        accident.setRules(accidentService.makeRules(req.getParameterValues("rIds")));
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
        model.addAttribute("types", accidentService.getAccidentTypes());
        model.addAttribute("rules", accidentService.getRules());
        return "editAccident";
    }

    @PostMapping("/updateAccident")
    public String update(@ModelAttribute Accident accident, HttpServletRequest req) {
        accident.setRules(accidentService.makeRules(req.getParameterValues("rIds")));
        accidentService.update(accident);
        return "redirect:/index";
    }
}