package com.godigital.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.godigital.backend.dto.ResumoConsultasDTO;
import com.godigital.backend.models.Consulta;
import com.godigital.backend.models.StatusConsulta;
import com.godigital.backend.models.Medico;
import com.godigital.backend.models.Paciente;
import com.godigital.backend.repository.ConsultaRepository;
import com.godigital.backend.repository.MedicoRepository;
import com.godigital.backend.repository.PacienteRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class ConsultaService {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private MedicoRepository medicoRepository;

   public List<Consulta> getConsultasPorEspecialidadeEMedico(String especialidade, LocalDate data) {
    List<Medico> medicos = medicoRepository.findByEspecialidade(especialidade);
    return consultaRepository.findByMedicoInAndData(medicos, data);
}
    public boolean existeConsultaNoHorario(Medico medico, LocalDate data, LocalTime horario) {
        return consultaRepository.existsByMedicoAndDataAndHorario(medico, data, horario);
    }

    public List<LocalTime> listarHorariosDisponiveis(Long medicoId, LocalDate data) {
        List<LocalTime> horariosPossiveis = List.of(
            LocalTime.of(9, 0),
            LocalTime.of(10, 0),
            LocalTime.of(11, 0),
            LocalTime.of(14, 0),
            LocalTime.of(15, 0),
            LocalTime.of(16, 0)
        );

        Medico medico = medicoRepository.findById(medicoId)
            .orElseThrow(() -> new RuntimeException("Médico não encontrado"));

        List<Consulta> consultasMarcadas = consultaRepository.findByMedicoAndData(medico, data);

        return horariosPossiveis.stream()
            .filter(h -> consultasMarcadas.stream().noneMatch(c -> c.getHorario().equals(h)))
            .toList();
    }

    public Consulta agendarConsulta(Long pacienteId, Long medicoId, String especialidade, String motivo, LocalDate data, LocalTime horario) {
        Paciente paciente = pacienteRepository.findById(pacienteId)
            .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));

        Medico medico;

        if (medicoId != null) {
            medico = medicoRepository.findById(medicoId)
                .orElseThrow(() -> new RuntimeException("Médico não encontrado"));
        } else {
            List<Medico> medicos = medicoRepository.findByEspecialidade(especialidade);
            Optional<Medico> disponivel = medicos.stream()
                .filter(m -> !consultaRepository.existsByMedicoAndDataAndHorario(m, data, horario))
                .findFirst();

            if (disponivel.isEmpty()) {
                throw new RuntimeException("Nenhum médico disponível nesse horário.");
            }
            medico = disponivel.get();
        }

        if (consultaRepository.existsByMedicoAndDataAndHorario(medico, data, horario)) {
            throw new RuntimeException("Este horário já está ocupado.");
        }

        Consulta consulta = new Consulta();
        consulta.setPaciente(paciente);
        consulta.setMedico(medico);
        consulta.setData(data);
        consulta.setHorario(horario);
        consulta.setMotivo(motivo);

        return consultaRepository.save(consulta);
    }

    public void cancelarConsultaPorPaciente(Long consultaId) {
        Consulta consulta = consultaRepository.findById(consultaId)
            .orElseThrow(() -> new RuntimeException("Consulta não encontrada"));

        consulta.setStatus(StatusConsulta.CANCELADA_PACIENTE);
        consultaRepository.save(consulta);
    }

    public List<Consulta> listarTodasConsultas() {
        return consultaRepository.findAll();
    }

    public void cancelarConsultaPorFuncionario(Long consultaId) {
        Consulta consulta = consultaRepository.findById(consultaId)
            .orElseThrow(() -> new RuntimeException("Consulta não encontrada"));

        consulta.setStatus(StatusConsulta.CANCELADA_FUNCIONARIO);
        consultaRepository.save(consulta);
    }

    public ResumoConsultasDTO gerarResumoConsultas() {
        Long totalAgendadas = consultaRepository.countByStatus(StatusConsulta.AGENDADA);
        Long totalEspera = consultaRepository.countByStatus(StatusConsulta.AGUARDANDO_CONFIRMACAO);
        Long totalCanceladas = consultaRepository.countByStatus(StatusConsulta.CANCELADA_PACIENTE);

        return new ResumoConsultasDTO(totalAgendadas, totalEspera, totalCanceladas);
    }


}
