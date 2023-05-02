package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.service.AccidentService;
import ru.job4j.accidents.service.AccidentTypeService;
import ru.job4j.accidents.service.RuleService;

import javax.servlet.http.HttpServletRequest;

@Controller
@AllArgsConstructor
public class AccidentController {

    private final AccidentService accidentService;

    private final AccidentTypeService typeService;

    private final RuleService ruleService;

    @GetMapping("/saveAccident")
    public String viewCreateAccident(Model model) {
        model.addAttribute("types", typeService.getAllTypes());
        model.addAttribute("rules", ruleService.getAllRules().stream().toList());
        return "createAccident";
    }

    @PostMapping("/saveAccident")
    public String save(Model model, @ModelAttribute Accident accident, HttpServletRequest req) {
        String[] ids = req.getParameterValues("rIds");
        int typeId = accident.getType().getId();
        accidentService.save(accident, typeId, ids);
        if (!accidentService.save(accident, typeId, ids)) {
            model.addAttribute("message", "The accident wasn't saved!");
            return "templates/errors/404";
        }
        return "redirect:/index";
    }

    @GetMapping("/{id}")
    public String viewEditAccident(Model model, @PathVariable int id) {
        var accidentOptional = accidentService.findById(id);
        if (accidentOptional.isEmpty()) {
            model.addAttribute("message", "The accident with such \"id\" is not found");
            return "templates/errors/404";
        }
        model.addAttribute("accident", accidentOptional.get());
        model.addAttribute("types", typeService.getAllTypes());
        model.addAttribute("rules", ruleService.getAllRules().stream().toList());
        return "editAccident";
    }

    @PostMapping("/updateAccident")
    public String update(@ModelAttribute Accident accident) {
        if (!accidentService.update(accident)) {
            return "shared/error404";
        }
        return "redirect:/index";
    }
}