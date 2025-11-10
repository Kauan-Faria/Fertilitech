package com.godigital.backend.service;

import com.godigital.backend.dto.AgendamentoDTO;
import com.godigital.backend.dto.ConsultaCreateDTO;
import com.godigital.backend.dto.ResumoConsultasDTO;
import com.godigital.backend.models.Consulta;
import com.godigital.backend.models.StatusConsulta;
import com.godigital.backend.models.Paciente;
import com.godigital.backend.models.Medico;
import com.godigital.backend.repository.ConsultaRepository;
import com.godigital.backend.repository.PacienteRepository;
import com.godigital.backend.repository.MedicoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConsultaService {

    private final ConsultaRepository consultaRepository;
    private final PacienteRepository pacienteRepository;
    private final MedicoRepository medicoRepository;

    public ConsultaService(ConsultaRepository consultaRepository,
                           PacienteRepository pacienteRepository,
                           MedicoRepository medicoRepository) {
        this.consultaRepository = consultaRepository;
        this.pacienteRepository = pacienteRepository;
        this.medicoRepository = medicoRepository;
    }

    public List<Consulta> listarTodas() {
        return consultaRepository.findAll();
    }

    public Optional<Consulta> buscarPorId(Long id) {
        return consultaRepository.findById(id);
    }

    public Consulta criarConsulta(AgendamentoDTO dto) {
        Paciente paciente = pacienteRepository.findById(dto.getPacienteId())
            .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));
        Medico medico = medicoRepository.findById(dto.getMedicoId())
            .orElseThrow(() -> new RuntimeException("Medico não encontrado"));

        Consulta c = new Consulta();
        c.setPaciente(paciente);
        c.setMedico(medico);
        c.setMotivo(dto.getMotivo());
        c.setData(dto.getData());
        c.setHorario(dto.getHorario());
        return consultaRepository.save(c);
    }


    public Consulta atualizarConsulta(Long id, ConsultaCreateDTO dto) {
        Optional<Consulta> opt = consultaRepository.findById(id);
        if(opt.isEmpty()) return null;

        Consulta consulta = opt.get();
        consulta.setPaciente(pacienteRepository.findById(dto.getPacienteId())
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado")));
        consulta.setMedico(medicoRepository.findById(dto.getMedicoId())
                .orElseThrow(() -> new RuntimeException("Médico não encontrado")));
        consulta.setMotivo(dto.getMotivo());
        consulta.setData(dto.getData());
        consulta.setHorario(dto.getHorario());

        return consultaRepository.save(consulta);
    }

    public void deletar(Long id) {
        consultaRepository.deleteById(id);
    }

    public void cancelarConsultaPorFuncionario(Long id) {
        Consulta consulta = consultaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Consulta não encontrada"));
        consulta.setStatus(StatusConsulta.CANCELADA);
        consultaRepository.save(consulta);
    }

    public ResumoConsultasDTO gerarResumoConsultas() {
        // exemplo simples
        long total = consultaRepository.count();
        long agendadas = consultaRepository.findAll().stream()
                .filter(c -> c.getStatus() == StatusConsulta.AGENDADA).count();
        long canceladas = total - agendadas;

        ResumoConsultasDTO resumo = new ResumoConsultasDTO();
        resumo.setTotalConsultas(total);
        resumo.setConsultasAgendadas(agendadas);
        resumo.setConsultasCanceladas(canceladas);
        return resumo;
    }
}
