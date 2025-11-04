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
        users.add(new User("Professor", "passprof", "Professor",null));
        users.add(new User("Ta1", "passta1", "TA",null));
        users.add(new User("Ta2", "passta2", "TA",null));

        //Group 1
        users.add(new User("Student1", "passstud1", "Student", "group_1"));
        users.add(new User("Student2", "passstud2", "Student", "group_1"));
        users.add(new User("Student3", "passstud3", "Student", "group_1"));
        users.add(new User("Student4", "passstud4", "Student", "group_1"));
        users.add(new User("Student5", "passstud5", "Student", "group_1"));

        // Group 2
        users.add(new User("Student6", "passstud6", "Student", "group_2"));
        users.add(new User("Student7", "passstud7", "Student", "group_2"));
        users.add(new User("Student8", "passstud8", "Student", "group_2"));
        users.add(new User("Student9", "passstud9", "Student", "group_2"));
        users.add(new User("Student10", "passstud10", "Student", "group_2"));

        //Group 3
        users.add(new User("Student11", "passstud11", "Student", "group_3"));
        users.add(new User("Student12", "passstud12", "Student", "group_3"));
        users.add(new User("Student13", "passstud13", "Student", "group_3"));
        users.add(new User("Student14", "passstud14", "Student", "group_3"));
        users.add(new User("Student15", "passstud15", "Student", "group_3"));

        //Group 4
        users.add(new User("Student16", "passstud16", "Student", "group_4"));
        users.add(new User("Student17", "passstud17", "Student", "group_4"));
        users.add(new User("Student18", "passstud18", "Student", "group_4"));
        users.add(new User("Student19", "passstud19", "Student", "group_4"));
        users.add(new User("Student20", "passstud20", "Student", "group_4"));

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

    public List<User> getUsersByGroup(String group){
        List<User> groupMembers = new ArrayList<>();
        for (User user: users){
            if(user.getGroup() != null && user.getGroup().equals(group)) groupMembers.add(user);
        }
        return groupMembers;
    }

}
