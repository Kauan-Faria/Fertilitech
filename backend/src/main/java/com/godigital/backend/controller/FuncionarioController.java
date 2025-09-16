package com.godigital.backend.controller;

import com.godigital.backend.models.Funcionario;
import com.godigital.backend.service.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/funcionarios")
public class FuncionarioController {

    @Autowired
    private FuncionarioService funcionarioService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String email, @RequestParam String senha) {
        Optional<Funcionario> funcionario = funcionarioService.buscarPorEmail(email);
    
        if (funcionario.isPresent() && funcionarioService.verificarSenha(senha, funcionario.get().getSenha())) {
            return ResponseEntity.ok("Login realizado com sucesso!");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas!");
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<String> criarFuncionario(@RequestBody Funcionario funcionario) {
        Optional<Funcionario> existente = funcionarioService.buscarPorEmail(funcionario.getEmail());
        if (existente.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("E-mail já cadastrado!");
        }
        funcionarioService.salvar(funcionario);
        return ResponseEntity.status(HttpStatus.CREATED).body("Funcionário cadastrado com sucesso!");
    }
}
