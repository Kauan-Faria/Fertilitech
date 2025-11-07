package com.godigital.backend.repository;

import com.godigital.backend.models.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

//para acessar o banco de dados mais facil
public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    Optional<Paciente> findByEmail(String email);
}