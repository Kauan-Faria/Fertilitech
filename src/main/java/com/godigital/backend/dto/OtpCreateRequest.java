package com.godigital.backend.dto;

public class OtpCreateRequest {
    private String username;
    private String notification;

    public String getUsername() {
        return username;
    }

    public String getNotification() {
        return notification;
    }

    // setters também, se quiser usar com @RequestBody
    public void setUsername(String username) {
        this.username = username;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }
}
