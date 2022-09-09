package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    @GetMapping("/user")
    public String showUser(Model model) {
        model.addAttribute("authuser", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return "user";
    }

}
