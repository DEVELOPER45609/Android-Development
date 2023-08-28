package com.example.universityattendanceapp;

public class ReadWriteUserDetails {
    private String Email;

    public ReadWriteUserDetails() {
        // Default constructor required for Firebase
    }

    public ReadWriteUserDetails(String email) {
        this.Email = email;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        this.Email = email;
    }
}

