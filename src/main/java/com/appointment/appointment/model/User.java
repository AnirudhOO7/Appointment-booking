package com.appointment.appointment.model;

public class User {
    private String username;
    private String password;
    private String role;
    private String group;

    // Constructor
    public User(String username, String password, String role, String group) {
        this.username = username;
        this.password = password;
        this.group = group;
        this.role = role;
    }

    // Getters
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public String getGroup(){
        return group;
    }
}