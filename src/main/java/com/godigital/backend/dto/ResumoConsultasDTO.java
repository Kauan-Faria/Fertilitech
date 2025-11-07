package com.godigital.backend.dto;

public class ResumoConsultasDTO {

    private long totalConsultas;
    private long consultasAgendadas;
    private long consultasCanceladas;

    // Getters e setters
    public long getTotalConsultas() {
        return totalConsultas;
    }

    public void setTotalConsultas(long totalConsultas) {
        this.totalConsultas = totalConsultas;
    }

    public long getConsultasAgendadas() {
        return consultasAgendadas;
    }

    public void setConsultasAgendadas(long consultasAgendadas) {
        this.consultasAgendadas = consultasAgendadas;
    }

    public long getConsultasCanceladas() {
        return consultasCanceladas;
    }

    public void setConsultasCanceladas(long consultasCanceladas) {
        this.consultasCanceladas = consultasCanceladas;
    }
}
