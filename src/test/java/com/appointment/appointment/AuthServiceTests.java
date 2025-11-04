package com.appointment.appointment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import com.appointment.appointment.service.AuthService;
import com.appointment.appointment.model.User;

import java.util.List;

public class AuthServiceTests {


    private AuthService authService = new AuthService();

    @Test
    void testValidateUser_Sucessful(){
        User user = authService.validateUser("Professor","passprof");
        assertNotNull(user,"User should be found");
        assertEquals("Professor", user.getRole());
    }


    @Test
    void testValidateUser_Failure(){
        User user = authService.validateUser("NonExistence","wrong");
        assertNull(user, "User should not be found");
    }
    

    @Test
    void testGetUserByGroup(){
        List<User> group=authService.getUsersByGroup("group_1");
        assertEquals(5, group.size(),"Group should have 5 users");

        for (User u:group){
            assertEquals("group_1", u.getGroup());
        }
    }
}
