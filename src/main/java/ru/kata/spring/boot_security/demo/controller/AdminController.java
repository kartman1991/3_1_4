package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.exceptions.TemplateProcessingException;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping()
//    @ExceptionHandler({TemplateProcessingException.class})
    public String users(Model model) {
        model.addAttribute("users", userService.findAll());
        model.addAttribute("user1", new User());
        model.addAttribute("authuser", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return "users";
    }
    @PostMapping()
    public String create(Model model, User user) {
        model.addAttribute("user", user);
        userService.save(user);
        return "redirect:/admin";
    }
    @GetMapping("/new")
    public String newUser(Model model) {
        model.addAttribute("newuser", new User());
        model.addAttribute("authuser", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return "admin/new";
    }
    @PostMapping("/edit/{id}")
    public String update(@ModelAttribute("user") User user) {
        userService.update(user);
        return "redirect:/admin";
    }
//    @GetMapping("/update/{id}")
//    public String editUser(Model model, @PathVariable("id") Long id) {
//        model.addAttribute("user", userService.findById(id));
//        model.addAttribute("authuser", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
//        return "admin/update";
//    }

    @GetMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        userService.delete(id);
        return "redirect:/admin";
    }
}
