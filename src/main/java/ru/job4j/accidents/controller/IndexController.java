package ru.job4j.accidents.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.accidents.service.AccidentService;

@Controller
public class IndexController {

    private AccidentService accidentService;

    @GetMapping("/")
    public String getIndex(Model model) {
        model.addAttribute("accidents", accidentService.findAll());
        return "index";
    }
}