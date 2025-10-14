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
