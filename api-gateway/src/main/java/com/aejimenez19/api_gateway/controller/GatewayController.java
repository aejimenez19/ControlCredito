package com.aejimenez19.api_gateway.controller;

import com.aejimenez19.api_gateway.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;


import java.util.Enumeration;

@RestController
public class GatewayController {
    @Autowired
    private JwtUtil jwtUtil;

    private final String AUTH_SERVICE = "http://localhost:8081"; // auth-service
    private final String CREDITO_SERVICE = "http://localhost:8082";

    private final RestTemplate restTemplate = new RestTemplate();

    @RequestMapping("/**")
    public ResponseEntity<?> gateway(HttpServletRequest request, HttpEntity<String> httpEntity) {

        String path = request.getRequestURI();

        // Si es para autenticación, no requiere token
        if (path.startsWith("/auth")) {
            return forwardRequest(request, httpEntity, AUTH_SERVICE);
        }

        // Si es para controlCredito, requiere token
        if (path.startsWith("/prestador")) {
            String authHeader = request.getHeader("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Falta token");
            }

            String token = authHeader.substring(7);
            if (!jwtUtil.validateToken(token)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido o vencido");
            }

            String userId = jwtUtil.getUserIdFromToken(token);

            // Redirigir agregando cabecera X-User-Id
            HttpHeaders newHeaders = new HttpHeaders();
            newHeaders.putAll(httpEntity.getHeaders());
            newHeaders.add("X-User-Id", userId);

            HttpEntity<String> newRequest = new HttpEntity<>(httpEntity.getBody(), newHeaders);
            return forwardRequest(request, newRequest, CREDITO_SERVICE);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ruta no reconocida");
    }

    private ResponseEntity<?> forwardRequest(HttpServletRequest request, HttpEntity<String> entity, String targetService) {
        String path = request.getRequestURI();
        String query = request.getQueryString();
        String url = targetService + path + (query != null ? "?" + query : "");

        HttpMethod method = HttpMethod.valueOf(request.getMethod());
        return restTemplate.exchange(url, method, entity, String.class);
    }
}
