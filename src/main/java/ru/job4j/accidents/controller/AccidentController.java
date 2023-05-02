package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.service.AccidentService;
import ru.job4j.accidents.service.AccidentTypeService;

@Controller
@AllArgsConstructor
public class AccidentController {

    private final AccidentService accidentService;

    private final AccidentTypeService typeService;

    @GetMapping("/saveAccident")
    public String viewCreateAccident(Model model) {
        typeService.getAllTypes();
        model.addAttribute("types", typeService.getAllTypes());
        return "createAccident";
    }

    @PostMapping("/saveAccident")
    public String save(@ModelAttribute Accident accident) {
        accidentService.save(accident);
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