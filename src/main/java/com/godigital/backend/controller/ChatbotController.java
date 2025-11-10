package com.godigital.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

@RestController
@RequestMapping("/chatbot")
public class ChatbotController {

    private static final String GEMINI_API_KEY = "AIzaSyCP6NLGQg0ZYSOKYyos-wpw86Go-AlK7V4"; 

    @PostMapping("/message")
    public ResponseEntity<Map<String, String>> respond(@RequestBody Map<String, String> payload) {
        String userMessage = payload.get("message");

        // Validação de tema permitido
        if (!isRelevantQuestion(userMessage)) {
            return ResponseEntity.ok(Map.of("response", 
                "Desculpe, posso responder apenas perguntas relacionadas à clínica, agendamentos, exames ou consultas."));
        }

        try {
            String response = callGeminiApi(userMessage);
            return ResponseEntity.ok(Map.of("response", response));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("response", "Erro ao processar a mensagem com IA."));
        }
    }

    // Verifica se a pergunta está dentro do escopo clínico
    private boolean isRelevantQuestion(String message) {
        if (message == null) return false;

        String lowerMsg = message.toLowerCase();

        String[] allowedKeywords = {
            "consulta", "agendamento", "horário", "médico", "clínica", "exame", "clinica",
            "retorno", "especialidade", "convênio", "atendimento", "plano de saúde", "paciente", "convenio"
        };

        for (String keyword : allowedKeywords) {
            if (lowerMsg.contains(keyword)) {
                return true;
            }
        }

        return false;
    }

    // Chama a API do Gemini com o prompt
     private String callGeminiApi(String prompt) throws Exception {
        HttpClient client = HttpClient.newHttpClient();

        // Informações sobre a clínica para contextualização
        String clinicContext = "Você é um assistente virtual para a Clínica Reproduser. " +
                               "localizada em Recife, Pernambuco, é uma microempresa dedicada à saúde reprodutiva. Através do Instagram @clinicareproduser, a clínica mantém um canal de comunicação ativo com seus pacientes e o público em geral, " +
                               "Estamos abertos de segunda a sexta, das 8h às 18h. " +
                               "Para agendamentos o paciente pode usar o site FertiliTech, onde pode escoher o medico, horario " +
                               "Aceitamos os convênios A, B e C. ";

        String fullPrompt = clinicContext + "\n\nPergunta do usuário: " + prompt;

        JSONObject requestBody = new JSONObject();
        JSONArray contentArray = new JSONArray();
        JSONObject userContent = new JSONObject();
        userContent.put("role", "user");

        JSONObject textPart = new JSONObject();
        textPart.put("text", fullPrompt); // Use o fullPrompt aqui

        userContent.put("parts", new JSONArray().put(textPart));
        contentArray.put(userContent);
        requestBody.put("contents", contentArray);

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://generativelanguage.googleapis.com/v1/models/gemini-1.5-flash:generateContent?key=" + GEMINI_API_KEY))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
            .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String responseBody = response.body();

        if (response.statusCode() != 200) {
            System.err.println("Erro na API Gemini - Status: " + response.statusCode() + ", Corpo: " + responseBody);
            throw new Exception("Erro ao se comunicar com a API do Gemini. Status: " + response.statusCode() + " - Detalhes: " + responseBody);
        }

        try {
            JSONObject jsonResponse = new JSONObject(responseBody);

            if (jsonResponse.has("candidates") && !jsonResponse.getJSONArray("candidates").isEmpty()) {
                JSONObject candidate = jsonResponse.getJSONArray("candidates").getJSONObject(0);
                if (candidate.has("content")) {
                    JSONObject content = candidate.getJSONObject("content");
                    if (content.has("parts") && !content.getJSONArray("parts").isEmpty()) {
                        JSONArray parts = content.getJSONArray("parts");
                        JSONObject firstPart = parts.getJSONObject(0);
                        if (firstPart.has("text")) {
                            return firstPart.getString("text");
                        }
                    }
                }
            }
            System.err.println("Resposta da API Gemini com estrutura inesperada: " + responseBody);
            throw new Exception("Resposta da API Gemini em formato inesperado. Verifique a documentação.");

        } catch (JSONException e) {
            System.err.println("Erro ao parsear a resposta JSON da API Gemini: " + e.getMessage());
            System.err.println("Corpo da resposta: " + responseBody);
            throw new Exception("Não foi possível processar a resposta da API do Gemini. Formato inválido.");
        }
    }
}
