package com.appointment.appointment.controller;

import com.appointment.appointment.model.User;
import com.appointment.appointment.service.AuthService;

import jakarta.servlet.http.HttpSession;

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

    //endpoint to show login page
    @GetMapping("/")
    public String login() {
        return "login";
    }

    //endpoint to handle login
    @PostMapping("/")
    public String handleLogin(
            @RequestParam("user") String username,
            @RequestParam("pass") String password,
            HttpSession session,
            Model model
    ) {

    User user = authService.validateUser(username, password);

    if(user != null) {
    session.setAttribute("loggedInUser", user);
    switch(user.getRole()) {
        case "Professor": return "redirect:/professor_dashboard";
        case "TA": return "redirect:/ta_dashboard";
        case "Student": return "redirect:/student_dashboard";
        default:
            model.addAttribute("error", "Unknown role");
            return "login";
    }
} else {
    model.addAttribute("error", "Invalid username or password");
    return "login";
}
    }

    //endpoint to handle professor dashboard
    @GetMapping("/professor_dashboard")
    public String professor_dashboard_session(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null || !user.getRole().equals("Professor")) {
            model.addAttribute("error", "Access denied. Please log in as a professor.");
            return "login";
        }
        model.addAttribute("username", user.getUsername());
        return "professor_dashboard";
    }

    //endpoint to handle logout
    @PostMapping("/logout")
    public String logout(HttpSession session, Model model) {
    session.invalidate(); 
    model.addAttribute("error", "You have been logged out successfully.");
    return "login"; 
}
}
