package com.appointment.appointment.controller;

import com.appointment.appointment.service.AppointmentGroupService;
import com.appointment.appointment.model.AppointmentGroup;
import com.appointment.appointment.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.servlet.http.HttpSession;


@Controller
public class ProfController {

    @Autowired
    private AppointmentGroupService appointmentGroupService;



    //Helper Func to validate professor session
    public User validateProfessorSession(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null || !user.getRole().equals("Professor")) { 
            model.addAttribute("error", "Access denied. Please log in as a professor.");
            return null;
        }

        model.addAttribute("username", user.getUsername());
        return user;
    }
    


    //to see the view group form    
    @GetMapping("/professor_dashboard")
    public String professorDashboardSession(HttpSession session, Model model) {
        User user = validateProfessorSession(session, model);
        if (user == null) return "login";
        return "professor_dashboard";
    }
    
    //to view all groups
    @GetMapping("/professor/view-groups")
    public String viewGroups(HttpSession session, Model model) {
        User user = validateProfessorSession(session, model);
        if (user == null) return "login";

        model.addAttribute("groups", appointmentGroupService.getALLGroups());
        return "professor_view_schedules";        
    }

    //to see the create group form
    @GetMapping("/professor/create-groups")
    public String CreateGroupForm(HttpSession session, Model model){
        User user = validateProfessorSession(session, model);
        if (user == null) return "login";
        return "professor_create_schedules";
    }

    //get the details from form and store
    @PostMapping("/professor/create-groups")
    public String createGroup(
        @RequestParam("groupType") String groupType,
        @RequestParam("appName") String appName,
        @RequestParam("appDate") String appDate,
        @RequestParam("startTime") String startTime,
        @RequestParam("endTime") String endTime,
        @RequestParam("slotDuration") Integer slotDuration,
        HttpSession session,
        Model model
    )
    
    {
        User user = validateProfessorSession(session, model);
        if(user == null) return "login";

        AppointmentGroup newGroup = new AppointmentGroup();
        newGroup.setGroupType(groupType);
        newGroup.setAppName(appName);
        newGroup.setAppDate(LocalDate.parse(appDate));
        newGroup.setStartTime(LocalTime.parse(startTime));
        newGroup.setEndTime(LocalTime.parse(endTime));
        newGroup.setSlotDuration(slotDuration);
        if (newGroup.getGroupType().equals("Individual")){
            newGroup.setMaxCapacity(1);
        } else {
            newGroup.setMaxCapacity(5); 
        }

        appointmentGroupService.saveAppointmentGroup(newGroup);

        model.addAttribute("message", "Appointment group created successfully.");
        return "professor_create_schedules";
        
    }

    
}
    
    

