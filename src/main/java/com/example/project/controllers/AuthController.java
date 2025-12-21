package com.example.project.controllers;

import com.example.project.models.User;
import com.example.project.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public String registerUser(@ModelAttribute User user, Model model) {
        System.out.println("Signup request received for: " + user.getEmail());
        try {
            userService.registerUser(user);
            System.out.println("User registered successfully");
            return "redirect:/login.html?success=true";
        } catch (RuntimeException e) {
            System.out.println("Signup failed: " + e.getMessage());
            return "redirect:/signup.html?error=" + e.getMessage();
        }
    }
}
