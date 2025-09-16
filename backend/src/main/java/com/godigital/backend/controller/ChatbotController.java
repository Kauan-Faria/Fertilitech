package com.godigital.backend.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/chatbot")
public class ChatbotController {

    @PostMapping("/message")
    public ResponseEntity<Map<String, String>> respond(@RequestBody Map<String, String> payload) {
        String userMessage = payload.get("message").toLowerCase().trim();

        String response = switch (userMessage) {
            case "como agendar uma consulta?", "como agendar uma consulta" ->
                "Para agendar uma consulta, você pode acessar o menu 'Agendamento' no nosso sistema, escolher o médico, a especialidade, e o horário desejado.";
            case "qual horário meu médico atende?", "qual horario meu medico atende?" ->
                "Os horários de atendimento variam conforme o médico. Você pode verificar os horários disponíveis no menu de agendamento selecionando seu médico.";
            case "quero cancelar minha consulta", "quero cancelar minha consulta?" ->
                "Para cancelar uma consulta, acesse 'Minhas Consultas', selecione a consulta que deseja cancelar e confirme o cancelamento.";
            case "que especialidades tem disponíveis?", "quais especialidades tem disponíveis?", "quais especialidades estão disponíveis?" ->
                "Temos várias especialidades, como cardiologia, dermatologia, ginecologia, neurologia, entre outras. Você pode conferir todas no menu de agendamento.";
            case "qual o endereço da clínica?", "qual o endereço da clinica?" ->
                "Nossa clínica fica na Rua João Cursino 811 A - Maurício de Nassau, Caruaru - PE.";
            case "qual o contato da clínica?", "qual o contato da clinica?" ->
                "Você pode entrar em contato pelo telefone (81) 3722-4350 ou pelo email reprodusermkt@gmail.com.";
            case "quais planos de saúde são aceitos?", "quais planos de saude sao aceitos?" ->
                "Aceitamos os principais planos como Unimed, Bradesco Saúde, SulAmérica, Amil.";
            case "como remarcar uma consulta?", "como remarcar uma consulta" ->
                "Para remarcar uma consulta, acesse 'Minhas Consultas', escolha a consulta que deseja alterar e selecione uma nova data e horário.";
            case "posso marcar consulta sem escolher médico?", "posso marcar consulta sem escolher medico?" ->
                "Sim, você pode marcar uma consulta escolhendo apenas a especialidade, e o sistema irá sugerir médicos disponíveis.";
            default ->
                "Desculpe, não entendi sua pergunta. Você pode perguntar sobre agendamento, cancelamento ou outras informações da clínica.";
        };

        Map<String, String> responseBody = Map.of("response", response);
        return ResponseEntity.ok(responseBody);
    }
}

