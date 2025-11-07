package com.godigital.backend.dto;

public class InformacoesSaudeDTO {
    private String planoSaude;
    private String numeroPlanoSaude;
    private String alergias;
    private String medicacoesDiarias;
    private String historicoMedicoFamiliar;
    private String diagnosticosAnteriores;

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
}

