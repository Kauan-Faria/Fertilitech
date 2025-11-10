package com.godigital.backend.controller;
import com.godigital.backend.controller.FuncionarioController;

public class LoginRequest {
    private String email;
    private String password; // ou "senha", depende do que você quer usar

    // Construtor vazio obrigatório
    public LoginRequest() {}

    // Getters e Setters
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
