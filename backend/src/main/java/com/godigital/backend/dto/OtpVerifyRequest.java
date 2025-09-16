package com.godigital.backend.dto;

public class OtpVerifyRequest {
    private String username;
    private String otp;

    public String getUsername() {
        return username;
    }

    public String getOtp() {
        return otp;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}
