package com.appointment.appointment.service;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.appointment.appointment.model.User;

import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthService {
    private List<User> users;


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
    
    //Helper Func to validate professor session
    public User validateStudentSession(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null || !user.getRole().equals("Student")) { 
            model.addAttribute("error", "Access denied. Please log in as a Student.");
            return null;
        }

        model.addAttribute("username", user.getUsername());
        return user;
    }
    
    //Helper Func to validate professor session
    public User validateTaSession(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null || !user.getRole().equals("TA")) { 
            model.addAttribute("error", "Access denied. Please log in as a TA.");
            return null;
        }

        model.addAttribute("username", user.getUsername());
        return user;
    }
    

    public AuthService(){

        users = new ArrayList<>();
        users.add(new User("Professor", "passprof", "Professor"));
        users.add(new User("Ta1", "passta1", "TA"));
        users.add(new User("Ta2", "passta2", "TA"));
        users.add(new User("Student1", "passstudent1", "Student"));
        users.add(new User("Student2", "passstudent2", "Student"));
        users.add(new User("Student3", "passstudent3", "Student"));
        users.add(new User("Student4", "passstudent4", "Student"));
        users.add(new User("Student5", "passstudent5", "Student"));
    }

    // Validate user details
    public User validateUser(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }
}
