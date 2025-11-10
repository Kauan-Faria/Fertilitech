package com.godigital.backend.service;

import com.godigital.backend.dto.PacienteSignupDTO;
import com.godigital.backend.dto.InformacoesSaudeDTO;
import com.godigital.backend.models.Paciente;
import com.godigital.backend.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PacienteService {

    private final PacienteRepository pacienteRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public PacienteService(PacienteRepository pacienteRepository, PasswordEncoder passwordEncoder) {
        this.pacienteRepository = pacienteRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Listar todos pacientes
    public List<Paciente> listarTodos() {
        return pacienteRepository.findAll();
    }

    // Buscar por ID
    public Optional<Paciente> buscarPorId(Long id) {
        return pacienteRepository.findById(id);
    }

    // Salvar paciente (recebe a entidade diretamente)
    public Paciente salvar(Paciente paciente) {
        if (pacienteRepository.findByEmail(paciente.getEmail()).isPresent()) {
            throw new RuntimeException("E-mail já cadastrado!");
        }
        paciente.setSenha(passwordEncoder.encode(paciente.getSenha()));
        return pacienteRepository.save(paciente);
    }

    // Cadastro via DTO (para o controller com @Valid)
    public Paciente cadastrarPaciente(PacienteSignupDTO dto) {
        if (pacienteRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("E-mail já cadastrado!");
        }

        Paciente paciente = new Paciente();
        paciente.setNome(dto.getNome());
        paciente.setTelefone(dto.getTelefone());
        paciente.setEmail(dto.getEmail());
        paciente.setSenha(passwordEncoder.encode(dto.getSenha()));
        paciente.setAutorizacaoDadosMedicos(dto.getAutorizacaoDadosMedicos());
        paciente.setPoliticaPrivacidade(dto.getPoliticaPrivacidade());


        return pacienteRepository.save(paciente);
    }

    // Autenticação
    public boolean autenticar(String email, String senha) {
        Optional<Paciente> paciente = pacienteRepository.findByEmail(email);
        return paciente.isPresent() && passwordEncoder.matches(senha, paciente.get().getSenha());
    }

    // Login retornando a entidade (opcional)
    public Paciente login(String email, String senha) {
        Optional<Paciente> pacienteOpt = pacienteRepository.findByEmail(email);
        if (pacienteOpt.isEmpty() || !passwordEncoder.matches(senha, pacienteOpt.get().getSenha())) {
            throw new RuntimeException("E-mail ou senha inválidos!");
        }
        return pacienteOpt.get();
    }

    // Deletar paciente
    public void deletar(Long id) {
        pacienteRepository.deleteById(id);
    }

    // Atualizar informações de saúde
    public Paciente atualizarInformacoesSaude(Long id, InformacoesSaudeDTO dto) {
        Paciente paciente = pacienteRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));

        paciente.setPlanoSaude(dto.getPlanoSaude());
        paciente.setNumeroPlanoSaude(dto.getNumeroPlanoSaude());
        paciente.setAlergias(dto.getAlergias());
        paciente.setMedicacoesDiarias(dto.getMedicacoesDiarias());
        paciente.setHistoricoMedicoFamiliar(dto.getHistoricoMedicoFamiliar());
        paciente.setDiagnosticosAnteriores(dto.getDiagnosticosAnteriores());

        return pacienteRepository.save(paciente);
    }
}
