package com.appointment.appointment.controller;
import com.appointment.appointment.repository.AppointmentGroupRepository;
import com.appointment.appointment.service.AppointmentGroupService;
import com.appointment.appointment.service.AuthService;
import com.appointment.appointment.model.AppointmentGroup;
import com.appointment.appointment.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.RequestBody;



@Controller
public class ProfController {

    private final AppointmentGroupRepository appointmentGroupRepository;

    @Autowired
    private AppointmentGroupService appointmentGroupService;

    @Autowired
    private AuthService authService;


    ProfController(AppointmentGroupRepository appointmentGroupRepository) {
        this.appointmentGroupRepository = appointmentGroupRepository;
    }



    //to see the view group form    
    @GetMapping("/professor_dashboard")
    public String professorDashboardSession(HttpSession session, Model model) {
        User user = authService.validateProfessorSession(session, model);
        if (user == null) return "login";
        return "professor_dashboard";
    }
    
    //to view all groups
    @GetMapping("/professor/view-groups")
    public String viewGroups(HttpSession session, Model model) {
        User user = authService.validateProfessorSession(session, model);
        if (user == null) return "login";

        model.addAttribute("groups", appointmentGroupService.getALLGroups());
        return "professor_view_schedules";        
    }

    //view edit form
    @GetMapping("/professor/edit-groups")
    public String viewEditGroups(HttpSession session, Model model) {
        User user = authService.validateProfessorSession(session, model);
        if (user == null) return "login";

        model.addAttribute("groups", appointmentGroupService.getALLGroups());
        return "professor_edit_schedules";        
    }

    //To delete a group
    @GetMapping("/professor/delete-groups")
    public String viewdeleteGroups(HttpSession session, Model model) {
        User user = authService.validateProfessorSession(session, model);
        if (user == null) return "login";

        model.addAttribute("groups", appointmentGroupService.getALLGroups());
        return "professor_delete_group";        
    }

    //to see the create group form
    @GetMapping("/professor/create-groups")
    public String CreateGroupForm(HttpSession session, Model model){
        User user = authService.validateProfessorSession(session, model);
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
        @RequestParam("maxCapacity") Integer maxCapacity,
        @RequestParam(value = "weeks", defaultValue = "1") Integer weeks,
        HttpSession session,
        Model model
    )
    
    {
        User user = authService.validateProfessorSession(session, model);
        if(user == null) return "login";

        LocalDate date = LocalDate.parse(appDate);

        for (int i=0;i<weeks;i++){
        AppointmentGroup newGroup = new AppointmentGroup();
        newGroup.setGroupType(groupType);
        newGroup.setAppName(appName);
        newGroup.setAppDate(date.plusWeeks(i));
        newGroup.setStartTime(LocalTime.parse(startTime));
        newGroup.setEndTime(LocalTime.parse(endTime));
        newGroup.setSlotDuration(slotDuration);
        newGroup.setMaxCapacity(groupType.equals("Individual")?1:maxCapacity);

    
        appointmentGroupService.saveAppointmentGroup(newGroup);
        }

        model.addAttribute("message", "Appointment group created successfully.");
        return "professor_create_schedules";
    
    }

    
    @GetMapping("/professor/edit-groups/{id}")
    public String editGroups(@PathVariable("id") Long id,HttpSession sesion, Model model){
        User user = authService.validateProfessorSession(sesion, model);
        if (user==null) return "login";

        AppointmentGroup group = appointmentGroupService.getGroupById(id);

        model.addAttribute("group", group);
        return "professor_update_group";
    }

    @PostMapping("/professor/edit-groups")
    public String updateGroups(@RequestParam("id") Long id,
    @RequestParam("groupType") String groupType,
    @RequestParam("appName") String appName,
    @RequestParam("appDate") String appDate,
    @RequestParam("startTime") String startTime,
    @RequestParam("endTime") String endTime,
    @RequestParam("slotDuration") Integer slotDuration,
    @RequestParam("maxCapacity") Integer maxCapacity,
    HttpSession session,
    Model model
    ){
        User user = authService.validateProfessorSession(session, model);
        if (user == null) return "login";

        AppointmentGroup group = appointmentGroupService.getGroupById(id);

        group.setGroupType(groupType);
        group.setAppName(appName);
        group.setAppDate(LocalDate.parse(appDate));
        group.setStartTime(LocalTime.parse(startTime));
        group.setEndTime(LocalTime.parse(endTime));
        group.setSlotDuration(slotDuration);
        group.setMaxCapacity(groupType.equals("Individual")?1:maxCapacity);
        appointmentGroupService.saveAppointmentGroup(group);
        return "redirect:/professor/edit-groups";
    }

    @PostMapping("/professor/delete-groups/{id}")
    public String deleteGroups(@PathVariable("id") Long id, HttpSession session, Model model){
        User user = authService.validateProfessorSession(session, model);
        if (user==null) return "login";

        appointmentGroupService.deleteGroupById(id);
        return "redirect:/professor/edit-groups";

    }
    
}

