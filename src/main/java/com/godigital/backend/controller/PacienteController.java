package com.godigital.backend.controller;

import com.godigital.backend.JwtUtil;
import com.godigital.backend.dto.*;
import com.godigital.backend.models.Paciente;
import com.godigital.backend.repository.PacienteRepository;
import com.godigital.backend.service.OtpService;
import com.godigital.backend.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import com.godigital.backend.models.Paciente;
import java.util.Map;



import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pacientes")

public class PacienteController {

    private final PacienteService pacienteService;
    private final PacienteRepository pacienteRepository;
    private final OtpService otpService;

    @Autowired
    public PacienteController(PacienteService pacienteService,
                              PacienteRepository pacienteRepository,
                              OtpService otpService) {
        this.pacienteService = pacienteService;
        this.pacienteRepository = pacienteRepository;
        this.otpService = otpService;
    }

    @PostMapping(value = "/signup", consumes = "application/json")
    public ResponseEntity<String> signup(@Valid @RequestBody PacienteSignupDTO dto) {
    pacienteService.cadastrarPaciente(dto);
    return ResponseEntity.status(HttpStatus.CREATED).body("Paciente cadastrado com sucesso!");
    }

    

    // Login
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@Valid @RequestBody LoginRequest loginRequest) {
        Paciente paciente = pacienteService.login(loginRequest.getEmail(), loginRequest.getSenha());

    String token = JwtUtil.gerarToken(paciente.getEmail());

    return ResponseEntity.ok(Map.of(
        "token", token,
        "message", "Login realizado com sucesso!"
    ));
    }

    // OTP
    @PostMapping("/otp/criar")
    public ResponseEntity<String> criarOtp(@Valid @RequestBody OtpCreateRequest request) {
        return otpService.criarOtp(request.getUsername(), request.getNotification());
    }

    @PostMapping("/otp/verificar")
    public ResponseEntity<String> verificarOtp(@Valid @RequestBody OtpVerifyRequest request) {
        return otpService.verificarOtp(request.getUsername(), request.getOtp());
    }

    // Atualizar informações de saúde
    @PutMapping("/{id}/informacoes-saude")
    public ResponseEntity<Paciente> atualizarInformacoesSaude(
            @PathVariable Long id,
            @Valid @RequestBody InformacoesSaudeDTO dto) {
        Paciente atualizado = pacienteService.atualizarInformacoesSaude(id, dto);
        return ResponseEntity.ok(atualizado);
    }

    // Autorização de uso de dados
    @PutMapping("/{id}/autorizacao")
    public ResponseEntity<?> autorizarUsoDados(
            @PathVariable Long id,
            @Valid @RequestBody AutorizacaoRequest autorizacaoRequest) {

        Optional<Paciente> pacienteOpt = pacienteRepository.findById(id);
        if (!pacienteOpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Paciente não encontrado.");
        }

        Paciente paciente = pacienteOpt.get();
        paciente.setAutorizacaoDadosMedicos(autorizacaoRequest.getAutorizacaoDadosMedicos());
        pacienteRepository.save(paciente);

        return ResponseEntity.ok("Autorização registrada com sucesso.");
    }

    // Listar todos pacientes
    @GetMapping
    public ResponseEntity<List<Paciente>> listarTodos() {
        return ResponseEntity.ok(pacienteRepository.findAll());
    }

    // Buscar por ID
    //@GetMapping("/{id}")
    //public ResponseEntity<Paciente> buscarPorId(@PathVariable Long id) {
       // return pacienteRepository.findById(id)
              //  .map(ResponseEntity::ok)
               // .orElse(ResponseEntity.notFound().build());
   // }

    // Atualizar paciente (nome, email)
    @PutMapping("/{id}")
    public ResponseEntity<Paciente> atualizar(@PathVariable Long id,
                                              @Valid @RequestBody PacienteSignupDTO dto) {
        return pacienteRepository.findById(id).map(p -> {
            p.setNome(dto.getNome());
            p.setEmail(dto.getEmail());
            p.setTelefone(dto.getTelefone());
            pacienteRepository.save(p);
            return ResponseEntity.ok(p);
        }).orElse(ResponseEntity.notFound().build());
    }

    // Deletar paciente
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!pacienteRepository.existsById(id)) return ResponseEntity.notFound().build();
        pacienteRepository.deleteById(id);
        return ResponseEntity.noContent().build();
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
