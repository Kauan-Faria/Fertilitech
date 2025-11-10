package com.godigital.backend.controller;

import com.godigital.backend.dto.AgendamentoDTO;
import com.godigital.backend.dto.ConsultaCreateDTO;
import com.godigital.backend.dto.ResumoConsultasDTO;
import com.godigital.backend.models.Consulta;
import com.godigital.backend.service.ConsultaService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import java.util.List;

@RestController
@RequestMapping("/consultas")
public class ConsultaController {

    private final ConsultaService consultaService;

    public ConsultaController(ConsultaService consultaService) {
        this.consultaService = consultaService;
    }

    @GetMapping
    public ResponseEntity<List<Consulta>> listarTodas() {
        return ResponseEntity.ok(consultaService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Consulta> buscarPorId(@PathVariable Long id) {
        return consultaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Consulta> criar(@RequestBody AgendamentoDTO dto) {
        Consulta nova = consultaService.criarConsulta(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nova);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Consulta> atualizar(@PathVariable Long id,
                                              @RequestBody ConsultaCreateDTO dto) {
        Consulta atualizada = consultaService.atualizarConsulta(id, dto);
        return atualizada != null ? ResponseEntity.ok(atualizada)
                                  : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        consultaService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/resumo")
    public ResponseEntity<ResumoConsultasDTO> obterResumo() {
        return ResponseEntity.ok(consultaService.gerarResumoConsultas());
    }

    @PutMapping("/funcionario/cancelar/{id}")
    public ResponseEntity<Void> cancelarPorFuncionario(@PathVariable Long id) {
        consultaService.cancelarConsultaPorFuncionario(id);
        return ResponseEntity.ok().build();
    }
}
