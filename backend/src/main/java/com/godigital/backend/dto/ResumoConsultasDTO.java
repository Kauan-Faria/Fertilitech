package com.godigital.backend.dto;

public class ResumoConsultasDTO {
     private Long totalAgendadas;
    private Long totalEmEspera;
    private Long totalCanceladasPaciente;

    public ResumoConsultasDTO(Long totalAgendadas, Long totalEmEspera, Long totalCanceladasPaciente) {
        this.totalAgendadas = totalAgendadas;
        this.totalEmEspera = totalEmEspera;
        this.totalCanceladasPaciente = totalCanceladasPaciente;
    }

    public Long getTotalAgendadas() {
        return totalAgendadas;
    }

    public Long getTotalEmEspera() {
        return totalEmEspera;
    }

    public Long getTotalCanceladasPaciente() {
        return totalCanceladasPaciente;
    }
}
