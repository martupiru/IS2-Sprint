package com.sprint.ejercicio2bCliente;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    private final RestTemplate restTemplate = new RestTemplate();
    private String token; // JWT almacenado tras el login

    public boolean login(String username, String password) {
        String url = "http://localhost:9000/auth/login";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> credentials = new HashMap<>();
        credentials.put("username", username);
        credentials.put("password", password);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(credentials, headers);

        try {
            ResponseEntity<TokenResponse> response =
                    restTemplate.postForEntity(url, request, TokenResponse.class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                this.token = response.getBody().getToken();
                return true;
            }
        } catch (Exception e) {
            System.out.println("Error de login: " + e.getMessage());
        }
        return false;
    }

    public String getToken() {
        return token;
    }

    /**
     * Helper para obtener HttpHeaders con el Authorization Bearer token.
     * Útil para llamar a endpoints protegidos después de hacer login.
     */
    public HttpHeaders authHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        if (token != null && !token.isEmpty()) {
            headers.setBearerAuth(token);
        }
        return headers;
    }

    // Clase interna para mapear la respuesta JSON { "token": "..." }
    public static class TokenResponse {
        private String token;
        public String getToken() { return token; }
        public void setToken(String token) { this.token = token; }
    }
}



