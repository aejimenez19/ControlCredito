package com.aejimenez19.auth_service.controller;

import com.aejimenez19.auth_service.dto.LoginRequest;
import com.aejimenez19.auth_service.dto.LoginResponse;
import com.aejimenez19.auth_service.dto.RegisterRequest;
import com.aejimenez19.auth_service.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UsuarioController {
    private final UsuarioService usuarioService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        usuarioService.register(request);
        return ResponseEntity.ok("Usuario registrado correctamente");
    }


    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        LoginResponse response = usuarioService.login(request.getUsuario(), request.getPassword());
        return ResponseEntity.ok(response);
    }

}
