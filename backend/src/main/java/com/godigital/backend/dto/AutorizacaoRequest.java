package com.godigital.backend.dto;

public class AutorizacaoRequest {

    private Boolean autorizacaoDadosMedicos;
    private Boolean politicaPrivacidade;

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