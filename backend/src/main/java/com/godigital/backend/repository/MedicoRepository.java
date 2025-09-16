package com.godigital.backend.repository;

import com.godigital.backend.models.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MedicoRepository extends JpaRepository<Medico, Long> {

    // Busca os médicos por especialidade
    List<Medico> findByEspecialidade(String especialidade);
}