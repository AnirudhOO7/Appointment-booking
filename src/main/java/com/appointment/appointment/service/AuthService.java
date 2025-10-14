package com.appointment.appointment.service;

import org.springframework.stereotype.Service;
import com.appointment.appointment.model.User;
import java.util.ArrayList;
import java.util.List;

@Service
public class AuthService {
    private List<User> users;

    public AuthService(){

        users = new ArrayList<>();
        users.add(new User("prof1", "pass123", "PROFESSOR"));
        users.add(new User("ta1", "pass123", "TA"));
        users.add(new User("student1", "pass123", "STUDENT"));
    }

    public boolean validateUser(String username, String password){
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }
}
