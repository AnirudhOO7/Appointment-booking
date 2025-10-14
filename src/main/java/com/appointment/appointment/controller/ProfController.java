package com.appointment.appointment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.appointment.appointment.model.User;

import jakarta.servlet.http.HttpSession;

@Controller
public class ProfController {
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
    
}
