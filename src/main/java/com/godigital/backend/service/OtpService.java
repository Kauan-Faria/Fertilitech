package com.godigital.backend.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.godigital.backend.dto.AccessTokenResponse;


@Service
public class OtpService {
     private final RestTemplate restTemplate = new RestTemplate();

    public String obterAccessToken() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("admin", "password");
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("client_id", "B1i5X4T5PKrZhwufjBF34tX2");
        body.add("client_secret", "K5Ci0EDcDgJxUyesXLxrRxIdvmouVYEpDDnXXxYd5TECbqU5");
        body.add("grant_type", "client_credentials");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

        ResponseEntity<AccessTokenResponse> response = restTemplate.postForEntity(
            "https://192.168.0.34/migracao/oauth/token",
            request,
            AccessTokenResponse.class
        );

        return response.getBody().getAccess_token();
    }

    public ResponseEntity<String> criarOtp(String username, String notification) {
        String token = obterAccessToken();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> payload = new HashMap<>();
        payload.put("async", true);
        payload.put("notification", notification);
        payload.put("username", username);
        payload.put("verbose", true);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(payload, headers);

        return restTemplate.postForEntity(
            "https://192.168.0.34/migracao/mfa/otp/create",
            request,
            String.class
        );
    }

    public ResponseEntity<String> verificarOtp(String username, String otp) {
        String token = obterAccessToken();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> payload = new HashMap<>();
        payload.put("username", username);
        payload.put("otp", otp);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(payload, headers);

        return restTemplate.postForEntity(
            "https://192.168.0.34/migracaoo/mfa/otp/verify",
            request,
            String.class
        );
    }
}
