package com.godigital.backend.controller;

import com.godigital.backend.dto.AgendamentoDTO;
import com.godigital.backend.dto.ResumoConsultasDTO;
import com.godigital.backend.models.Consulta;
import com.godigital.backend.service.ConsultaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


@RestController
@RequestMapping("/consultas")
public class ConsultaController {

    @Autowired
    private ConsultaService consultaService;

    @GetMapping("/horarios-disponiveis")
    public ResponseEntity<List<LocalTime>> getHorarios(
        @RequestParam Long medicoId,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data
    ) {
        return ResponseEntity.ok(consultaService.listarHorariosDisponiveis(medicoId, data));
    }

    @PostMapping("/agendar")
    public ResponseEntity<Consulta> agendarConsulta(@RequestBody AgendamentoDTO dto, @RequestParam LocalTime horario) {
        Consulta consulta = consultaService.agendarConsulta(
            dto.getPacienteId(),
            dto.getMedicoId(),
            dto.getEspecialidade(),
            dto.getMotivo(),
            dto.getData(),
            horario
        );
        return ResponseEntity.ok(consulta);
    }

    @PutMapping("/cancelar/{id}")
    public ResponseEntity<String> cancelarConsulta(@PathVariable Long id) {
        consultaService.cancelarConsultaPorPaciente(id);
        return ResponseEntity.ok("Consulta cancelada com sucesso.");
    }
    
    // Lista todas as consultas agendadas
    @GetMapping("/funcionario/todas")
    public ResponseEntity<List<Consulta>> listarTodasConsultas() {
        return ResponseEntity.ok(consultaService.listarTodasConsultas());
    }

    // Cancelar consulta como funcionário
    @PutMapping("/funcionario/cancelar/{id}")
    public ResponseEntity<Void> cancelarConsultaFuncionario(@PathVariable Long id) {
        consultaService.cancelarConsultaPorFuncionario(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/resumo")
    public ResponseEntity<ResumoConsultasDTO> obterResumoConsultas() {
        return ResponseEntity.ok(consultaService.gerarResumoConsultas());
    }

}

