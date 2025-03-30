package ru.tatarinov.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class StartController {
    @GetMapping()
    public String index() {
        return "index";
    }


    @GetMapping("/home")
    public String home() {
        return "redirect:/";
    }
}
