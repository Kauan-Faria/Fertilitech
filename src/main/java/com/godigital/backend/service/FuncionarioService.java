package com.godigital.backend.service;

import com.godigital.backend.models.Funcionario;
import com.godigital.backend.repository.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FuncionarioService {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Optional<Funcionario> buscarPorEmail(String email) {
        return funcionarioRepository.findByEmail(email);
    }

    public Funcionario salvar(Funcionario funcionario) {
        // Verifica se o e-mail já está cadastrado
        if (funcionarioRepository.findByEmail(funcionario.getEmail()).isPresent()) {
            throw new RuntimeException("E-mail já cadastrado!");
        }

        // Criptografa a senha antes de salvar
        funcionario.setPassword(passwordEncoder.encode(funcionario.getPassword()));

        return funcionarioRepository.save(funcionario);
    }

    public boolean autenticar(String email, String senha) {
        Optional<Funcionario> funcionario = funcionarioRepository.findByEmail(email);

        // Se o funcionário existir e a senha for válida, retorna true
        return funcionario.isPresent() && passwordEncoder.matches(senha, funcionario.get().getPassword());
    }

    public boolean verificarSenha(String senhaDigitada, String senhaArmazenada) {
        return passwordEncoder.matches(senhaDigitada, senhaArmazenada);
    }
}
