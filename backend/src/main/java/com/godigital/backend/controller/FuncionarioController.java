package com.godigital.backend.controller;

import com.godigital.backend.models.Funcionario;
import com.godigital.backend.service.FuncionarioService;
import com.godigital.backend.controller.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Optional;
import java.util.Map;


@RestController
@RequestMapping("/funcionarios")
public class FuncionarioController {

    @Autowired
    private FuncionarioService funcionarioService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
    String email = request.getEmail();
    String password = request.getPassword();

    Optional<Funcionario> funcionario = funcionarioService.buscarPorEmail(email);

    if (funcionario.isPresent() && funcionarioService.verificarSenha(password, funcionario.get().getPassword())) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Login realizado com sucesso!");
        return ResponseEntity.ok(response);
    } else {
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        response.put("message", "Credenciais inválidas!");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
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
