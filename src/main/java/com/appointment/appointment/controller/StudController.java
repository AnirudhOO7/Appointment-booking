package com.appointment.appointment.controller;

import com.appointment.appointment.model.Booking;
import com.appointment.appointment.service.AppointmentGroupService;
import com.appointment.appointment.service.AuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import jakarta.servlet.http.HttpSession;

import com.appointment.appointment.model.User;
import com.appointment.appointment.service.BookingService;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Controller
public class StudController {

    @Autowired
    private AuthService authService;

    @Autowired
    private AppointmentGroupService appointmentGroupService;

    @Autowired
    private BookingService bookingService;

    @GetMapping("/student_dashboard")
    public String studentDashboard(HttpSession session, Model model) {

    User user = authService.validateStudentSession(session, model);
    if (user == null) return "login";

    model.addAttribute("username", user.getUsername());
    return "student_dashboard";
    
    }
    @GetMapping("/student/view-groups")
    public String viewGroup(HttpSession session, Model model) {
        User user = authService.validateStudentSession(session, model);
        if (user == null) return "login";

        model.addAttribute("groups", appointmentGroupService.getALLGroups());

        List<Booking> userBookings = bookingService.getBookingsByUsername(user.getUsername());

        Set<Long> bookedSlotsIds = userBookings.stream()
            .map(booking -> booking.getTimeSlot().getId())
            .collect(Collectors.toSet());

        model.addAttribute("userBooking", bookedSlotsIds);
        return "student_view_schedules";
    }
    
    @GetMapping("/student/book-groups")
    public String bookGroup(HttpSession session, Model model) {
        User user = authService.validateStudentSession(session, model);
        if (user == null) return "login";

        model.addAttribute("groups", appointmentGroupService.getALLGroups());
        return "student_book_schedules";
    }

    @PostMapping("/student/book-groups")
    public String bookSlot(@RequestParam("timeSlotId") Long timeSlotId, HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        User user = authService.validateStudentSession(session, model);
        if (user == null) return "login";

        String bookingResult = bookingService.bookSlot(timeSlotId, user);
        if (bookingResult.equals("Booking Successful")){
            redirectAttributes.addFlashAttribute("success", bookingResult);
        } else {
            redirectAttributes.addFlashAttribute("error", bookingResult);
        }
        return "redirect:/student/book-groups";

    }

    

}
    