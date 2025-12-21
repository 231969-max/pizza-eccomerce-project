package com.example.project.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @org.springframework.beans.factory.annotation.Autowired
    private com.example.project.repositories.ProductRepository productRepository;

    @GetMapping("/")
    public String home(org.springframework.ui.Model model) {
        model.addAttribute("activePage", "home");
        model.addAttribute("products", productRepository.findAll());
        return "index";
    }

    @GetMapping("/index.html")
    public String index(org.springframework.ui.Model model) {
        model.addAttribute("activePage", "home");
        model.addAttribute("products", productRepository.findAll());
        return "index";
    }

    @GetMapping("/menu.html")
    public String menu(org.springframework.ui.Model model) {
        model.addAttribute("activePage", "menu");
        model.addAttribute("products", productRepository.findAll());
        return "menu";
    }

    @GetMapping("/about.html")
    public String about(org.springframework.ui.Model model) {
        model.addAttribute("activePage", "about");
        return "about";
    }

    @GetMapping("/gallery.html")
    public String gallery(org.springframework.ui.Model model) {
        model.addAttribute("activePage", "gallery");
        return "gallery";
    }

    @GetMapping("/contact.html")
    public String contact(org.springframework.ui.Model model) {
        model.addAttribute("activePage", "contact");
        return "contact";
    }

    @GetMapping("/login.html")
    public String login(org.springframework.ui.Model model) {
        model.addAttribute("activePage", "login");
        return "login";
    }

    @GetMapping("/signup.html")
    public String signup(org.springframework.ui.Model model) {
        model.addAttribute("activePage", "signup");
        return "signup";
    }
}
