package com.godigital.backend.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class AgendamentoDTO {
    private Long pacienteId;
    private Long medicoId;
    private String motivo;
    private LocalDate data;
    private LocalTime horario;

    // Getters e setters
    public Long getPacienteId() { return pacienteId; }
    public void setPacienteId(Long pacienteId) { this.pacienteId = pacienteId; }
    public Long getMedicoId() { return medicoId; }
    public void setMedicoId(Long medicoId) { this.medicoId = medicoId; }
    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }
    public LocalDate getData() { return data; }
    public void setData(LocalDate data) { this.data = data; }
    public LocalTime getHorario() { return horario; }
    public void setHorario(LocalTime horario) { this.horario = horario; }
}
