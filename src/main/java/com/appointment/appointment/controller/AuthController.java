package com.appointment.appointment.controller;

import com.appointment.appointment.service.AuthService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController{

    @Autowired
    private AuthService authService;

    @GetMapping("/")
    public String login() {
        return "login";
    }

    @PostMapping("/")
    public String handleLogin(
            @RequestParam("user") String username,
            @RequestParam("pass") String password,
            Model model
    ) {
        boolean isValid = authService.validateUser(username, password);

        if (isValid) {
            return "redirect:/dashboard";
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "login";
        }
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }

}
