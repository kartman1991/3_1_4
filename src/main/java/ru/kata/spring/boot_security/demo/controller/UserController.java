package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping(name = "/")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String users(Model model) {
        model.addAttribute("users", userService.findAll());
        return "users";
    }
    @PostMapping()
//    public String create(@ModelAttribute("user") User user) {
    public String create(Model model, User user) {
        boolean check = true;
        System.out.println(check);
        model.addAttribute("user", user);
        model.addAttribute("check", check);
        userService.save(user);
        return "redirect:/";
    }
    @GetMapping("/admin/new")
//    public String newUser(@ModelAttribute("user") User user) {
    public String newUser(Model model) {
        boolean check = true;
        model.addAttribute("user", new User());
        model.addAttribute("check", check);
        return "admin/new";
    }
    @PostMapping("/{id}")
    public String update(@ModelAttribute("user") User user) {
        userService.update(user);
        return "redirect:/";
    }
    @GetMapping("admin/update/{id}")
    public String editUser(Model model, @PathVariable("id") Long id) {
        model.addAttribute("user", userService.findById(id));
        return "admin/update";
    }

    @GetMapping("/admin/{id}")
    public String delete(@PathVariable("id") Long id) {
        userService.delete(id);
        return "redirect:/";
    }

    @GetMapping("/user")
    public String showUser(Model model) {
//        model.addAttribute("user", userService.);
        return "user";
    }
}
