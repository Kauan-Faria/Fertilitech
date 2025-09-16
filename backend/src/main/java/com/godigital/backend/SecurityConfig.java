package com.godigital.backend;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
@Configuration

public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Desativa CSRF para facilitar testes
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/funcionarios/login", "/funcionarios/signup", "/pacientes/login", "/pacientes/signup", "/pacientes/otp/criar", "/pacientes/otp/verificar", "/pacientes/{id}/informacoes-saude", "/pacientes/{id}/autorizacao", "/consultas/horarios-disponiveis", "/consultas/agendar", "/funcionario/todas","/funcionario/cancelar/{id}", "/resumo", "/message").permitAll() // Permite acesso público
                .anyRequest().authenticated() // Exige autenticação para o restante
            )
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}
