package com.godigital.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.godigital.backend.models.Paciente;
import com.godigital.backend.repository.PacienteRepository;

public class PacienteImporter {
    private final PacienteRepository pacienteRepository;

    public PacienteImporter(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    public void importarPacientesDeCsv(String caminhoArquivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            br.readLine(); // pula o cabeçalho
            while ((linha = br.readLine()) != null) {
                String[] campos = linha.split(",");
                if (campos.length >= 6) {
                    Paciente paciente = new Paciente();
                    paciente.setNome(campos[0]);
                    paciente.setEmail(campos[1]);
                    paciente.setTelefone(campos[4]);
                    paciente.setSenha(campos[5]); // de preferência, aplique hash

                    pacienteRepository.save(paciente);
                }
            }
            System.out.println("Pacientes importados com sucesso!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
