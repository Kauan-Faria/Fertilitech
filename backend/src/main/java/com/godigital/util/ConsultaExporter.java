package com.godigital.util;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.godigital.backend.models.Consulta;
import com.godigital.backend.repository.ConsultaRepository;

public class ConsultaExporter {
    private final ConsultaRepository consultaRepository;

    public ConsultaExporter(ConsultaRepository consultaRepository) {
        this.consultaRepository = consultaRepository;
    }

    public void exportarConsultasParaCsv(String caminhoArquivo) {
        List<Consulta> consultas = consultaRepository.findAll();

        try (FileWriter writer = new FileWriter(caminhoArquivo)) {
            writer.append("Paciente,Médico,Especialidade,Data,Horário,Status\n");
            for (Consulta c : consultas) {
                writer.append(c.getPaciente().getNome()).append(",")
                      .append(c.getMedico().getNome()).append(",")
                      .append(c.getMedico().getEspecialidade()).append(",")
                      .append(c.getData().toString()).append(",")
                      .append(c.getHorario().toString()).append(",")
                      .append(c.getStatus()).append("\n");
            }
            System.out.println("Consultas exportadas com sucesso!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
