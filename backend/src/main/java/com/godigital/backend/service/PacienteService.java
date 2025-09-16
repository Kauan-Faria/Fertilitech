package com.godigital.backend.service;

import com.godigital.backend.models.Paciente;
import com.godigital.backend.repository.PacienteRepository;
import com.godigital.backend.dto.InformacoesSaudeDTO;

// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;


@Service
public class PacienteService {

    private final PacienteRepository pacienteRepository;
    private final PasswordEncoder passwordEncoder;

    public PacienteService(PacienteRepository pacienteRepository, PasswordEncoder passwordEncoder) {
        this.pacienteRepository = pacienteRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Paciente> listarTodos() {
        return pacienteRepository.findAll();
    }

    public Optional<Paciente> buscarPorId(Long id) {
        return pacienteRepository.findById(id);
    }

    public Paciente salvar(Paciente paciente) {
        // Verifica se o e-mail já está cadastrado
        if (pacienteRepository.findByEmail(paciente.getEmail()).isPresent()) {
            throw new RuntimeException("E-mail já cadastrado!");
        }

        // Criptografa a senha antes de salvar
        paciente.setSenha(passwordEncoder.encode(paciente.getSenha()));

        return pacienteRepository.save(paciente);
    }

    public Paciente login(String email, String senha) {
        Optional<Paciente> pacienteOpt = pacienteRepository.findByEmail(email);

        if (pacienteOpt.isEmpty() || !passwordEncoder.matches(senha, pacienteOpt.get().getSenha())) {
            throw new RuntimeException("E-mail ou senha inválidos!");
        }

        return pacienteOpt.get();
    }

    public void deletar(Long id) {
        pacienteRepository.deleteById(id);
    }

    public Paciente cadastrarPaciente(Paciente paciente) {
        // Verifica se já existe um paciente com o mesmo e-mail
        if (pacienteRepository.findByEmail(paciente.getEmail()).isPresent()) {
            throw new RuntimeException("E-mail já cadastrado!");
        }

        // Criptografa a senha antes de salvar
        paciente.setSenha(passwordEncoder.encode(paciente.getSenha()));

        return pacienteRepository.save(paciente);
    }

    public boolean autenticar(String email, String senha) {
        Optional<Paciente> paciente = pacienteRepository.findByEmail(email);
        
        // Se o paciente existir e a senha estiver correta, retorna true
        return paciente.isPresent() && passwordEncoder.matches(senha, paciente.get().getSenha());
    }


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
