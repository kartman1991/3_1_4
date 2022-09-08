package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
    public String users(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        model.addAttribute("users", userService.findAll());
        model.addAttribute("auther", user.getEmail());
        model.addAttribute("roles", user.getStringUserAuthorities());
        return "users";
    }
    @PostMapping()
    public String create(Model model, User user) {
//        boolean check = true;
//        System.out.println(user.isAdmin());
        model.addAttribute("user", user);
//        model.addAttribute("check", check);
        userService.save(user);
        return "redirect:/admin";
    }
    @GetMapping("/new")
    public String newUser(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        model.addAttribute("user", new User());
        model.addAttribute("auther", user.getEmail());
        model.addAttribute("roles", user.getStringUserAuthorities());
        return "admin/new";
    }
    @PostMapping("/{id}")
    public String update(@ModelAttribute("user") User user) {
        userService.update(user);
        return "redirect:/admin";
    }
    @GetMapping("/update/{id}")
    public String editUser(Model model, @PathVariable("id") Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        model.addAttribute("user", userService.findById(id));
        model.addAttribute("auther", user.getEmail());
        model.addAttribute("roles", user.getStringUserAuthorities());
        return "admin/update";
    }

    @GetMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        userService.delete(id);
        return "redirect:/admin";
    }
}
