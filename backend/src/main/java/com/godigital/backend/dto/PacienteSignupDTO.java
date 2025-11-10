package com.godigital.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class PacienteSignupDTO {

    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    @NotBlank(message = "O telefone é obrigatório")
    private String telefone;

    @Email(message = "Email inválido")
    @NotBlank(message = "O email é obrigatório")
    private String email;

    @NotBlank(message = "A senha é obrigatória")
    @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres")
    private String senha;

    @NotNull(message = "É necessário autorizar o uso dos dados médicos")
    private Boolean autorizacaoDadosMedicos; 

    @NotNull(message = "É necessário aceitar a política de privacidade")
    private Boolean politicaPrivacidade;

    // Getters e setters
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public Boolean getAutorizacaoDadosMedicos() { return autorizacaoDadosMedicos; }
    public void setAutorizacaoDadosMedicos(Boolean autorizacaoDadosMedicos) { this.autorizacaoDadosMedicos = autorizacaoDadosMedicos; }

    public Boolean getPoliticaPrivacidade() { return politicaPrivacidade; }
    public void setPoliticaPrivacidade(Boolean politicaPrivacidade) { this.politicaPrivacidade = politicaPrivacidade; }
}
