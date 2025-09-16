package com.godigital.backend.controller;

import com.godigital.backend.dto.AutorizacaoRequest;
import com.godigital.backend.dto.InformacoesSaudeDTO;
import com.godigital.backend.dto.OtpCreateRequest;
import com.godigital.backend.dto.OtpVerifyRequest;
import com.godigital.backend.models.Paciente;
import com.godigital.backend.repository.PacienteRepository;
import com.godigital.backend.service.OtpService;
import com.godigital.backend.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pacientes")

public class PacienteController {
    private final PacienteService pacienteService;
   
    @Autowired
    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    // Signup (Cadastro de Paciente)
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody Paciente paciente) {
        pacienteService.cadastrarPaciente(paciente);
        return ResponseEntity.status(HttpStatus.CREATED).body("Paciente cadastrado com sucesso!");
    }

    // Login
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        boolean autenticado = pacienteService.autenticar(loginRequest.getEmail(), loginRequest.getSenha());
        if (autenticado) {
            return ResponseEntity.ok("Login realizado com sucesso! (Aqui retornaria um token JWT)");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email ou senha inválidos");
        }
    }
    @Autowired
    private OtpService otpService;

    @PostMapping("/otp/criar")
    public ResponseEntity<String> criarOtp(@RequestBody OtpCreateRequest request) {
        return otpService.criarOtp(request.getUsername(), request.getNotification());
    }

    @PostMapping("/otp/verificar")
    public ResponseEntity<String> verificarOtp(@RequestBody OtpVerifyRequest request) {
        return otpService.verificarOtp(request.getUsername(), request.getOtp());
    }

    @PutMapping("/{id}/informacoes-saude")
    public ResponseEntity<Paciente> atualizarInformacoesSaude(
            @PathVariable Long id,
            @RequestBody InformacoesSaudeDTO dto) {
        Paciente atualizado = pacienteService.atualizarInformacoesSaude(id, dto);
        return ResponseEntity.ok(atualizado);
    }
    
     @Autowired
    private PacienteRepository pacienteRepository;

    @PutMapping("/{id}/autorizacao")
    public ResponseEntity<?> autorizarUsoDados(
            @PathVariable Long id,
            @RequestBody AutorizacaoRequest autorizacaoRequest) {
    
        Optional<Paciente> pacienteOpt = pacienteRepository.findById(id);
    
        if (!pacienteOpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Paciente não encontrado.");
        }
    
        Paciente paciente = pacienteOpt.get();
    
        // Atualiza os campos de autorização no paciente existente
        paciente.setAutorizacaoDadosMedicos(autorizacaoRequest.getAutorizacaoDadosMedicos());
        paciente.setPoliticaPrivacidade(autorizacaoRequest.getPoliticaPrivacidade());
    
        // Salva o paciente atualizado
        pacienteRepository.save(paciente);
    
        return ResponseEntity.ok("Autorização registrada com sucesso.");
    }
    
}


// Classe auxiliar para o login
class LoginRequest {
    private String email;
    private String senha;
    
    // Getters e Setters
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
}
