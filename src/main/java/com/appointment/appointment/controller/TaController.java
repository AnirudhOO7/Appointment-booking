package com.appointment.appointment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.appointment.appointment.service.AppointmentGroupService;
import com.appointment.appointment.service.AuthService;
import com.appointment.appointment.model.User;

import org.springframework.ui.Model;
import jakarta.servlet.http.HttpSession;


@Controller
public class TaController {

    @Autowired
    private AuthService authService;

    @Autowired
    private AppointmentGroupService appointmentGroupService;

    @GetMapping("/ta_dashboard")
    public String viewSlots(HttpSession session,Model model){
    User user = authService.validateTaSession(session,model);
    if (user == null) return "login";
    
    model.addAttribute("groups", appointmentGroupService.getALLGroups());
    return "ta_dashboard";

    }

    
}
