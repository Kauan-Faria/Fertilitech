package com.godigital.backend.repository;

import com.godigital.backend.models.Consulta;
import com.godigital.backend.models.Medico;
import com.godigital.backend.models.StatusConsulta;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

    List<Consulta> findByMedicoAndData(Medico medico, LocalDate data);

    boolean existsByMedicoAndDataAndHorario(Medico medico, LocalDate data, LocalTime horario);

    List<Consulta> findByMedicoInAndData(List<Medico> medicos, LocalDate data);

    Long countByStatus(StatusConsulta status);

}
