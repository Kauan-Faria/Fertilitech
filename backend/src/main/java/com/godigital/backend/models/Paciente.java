package com.godigital.backend.models;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "pacientes")
//cria a tabela de pacientes no banco de dados
public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nome;
    private String email;
    private String telefone;
    private String senha;
    private LocalDate dataNascimento;
    private String endereco; 
    private String contatoEmergencia; 
    private String telefoneEmergencia;
    private String planoSaude;
    private String numeroPlanoSaude;
    private String alergias;
    private String medicacoesDiarias;
    private String historicoMedicoFamiliar;
    private String diagnosticosAnteriores;

    @Enumerated(EnumType.STRING)
    private Genero genero; 

    @Column(name = "autorizacao_dados_medicos", nullable = false)
    private Boolean autorizacaoDadosMedicos;

    @Column(name = "politica_privacidade", nullable = false)
    private Boolean politicaPrivacidade;

     // Getters e Setters
     public Long getId() { 
        return id; 
    }

     public void setId(Long id) {
         this.id = id;
     }

     public String getNome() { 
        return nome; 
    }

     public void setNome(String nome) { 
        this.nome = nome;
     }

     public String getEmail() { 
        return email; 
    }

     public void setEmail(String email) {
         this.email = email; 
     }

     public String getSenha() { 
        return senha;
     }
     public void setSenha(String senha) { 
        this.senha = senha; 
    }

     public String getPlanoSaude() {
        return planoSaude;
    }
    
    public void setPlanoSaude(String planoSaude) {
        this.planoSaude = planoSaude;
    }
    
    public String getNumeroPlanoSaude() {
        return numeroPlanoSaude;
    }
    
    public void setNumeroPlanoSaude(String numeroPlanoSaude) {
        this.numeroPlanoSaude = numeroPlanoSaude;
    }
    
    public String getAlergias() {
        return alergias;
    }
    
    public void setAlergias(String alergias) {
        this.alergias = alergias;
    }
    
    public String getMedicacoesDiarias() {
        return medicacoesDiarias;
    }
    
    public void setMedicacoesDiarias(String medicacoesDiarias) {
        this.medicacoesDiarias = medicacoesDiarias;
    }
    
    public String getHistoricoMedicoFamiliar() {
        return historicoMedicoFamiliar;
    }
    
    public void setHistoricoMedicoFamiliar(String historicoMedicoFamiliar) {
        this.historicoMedicoFamiliar = historicoMedicoFamiliar;
    }
    
    public String getDiagnosticosAnteriores() {
        return diagnosticosAnteriores;
    }
    
    public void setDiagnosticosAnteriores(String diagnosticosAnteriores) {
        this.diagnosticosAnteriores = diagnosticosAnteriores;
    }
    
    public Boolean getAutorizacaoDadosMedicos() {
        return autorizacaoDadosMedicos;
    }

    public void setAutorizacaoDadosMedicos(Boolean autorizacaoDadosMedicos) {
        this.autorizacaoDadosMedicos = autorizacaoDadosMedicos;
    }

    public Boolean getPoliticaPrivacidade() {
        return politicaPrivacidade;
    }

    public void setPoliticaPrivacidade(Boolean politicaPrivacidade) {
        this.politicaPrivacidade = politicaPrivacidade;
    }
}



