package com.aejimenez19.auth_service.service;

import com.aejimenez19.auth_service.configuration.JwtUtil;
import com.aejimenez19.auth_service.dto.LoginResponse;
import com.aejimenez19.auth_service.entity.Usuario;
import com.aejimenez19.auth_service.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PersonaService personaService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public Usuario register(String username, String password) {
        if (usuarioRepository.findByUsuario(username).isPresent()) {
            throw new RuntimeException("El usuario ya existe");
        }

        Usuario nuevo = new Usuario();
        nuevo.setUsuario(username);
        nuevo.setPassword(passwordEncoder.encode(password));
        nuevo.setEstado("Activo");

        return usuarioRepository.save(nuevo);
    }


    public LoginResponse login(String username, String password) {
        Usuario usuario = usuarioRepository.findByUsuario(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!passwordEncoder.matches(password, usuario.getPassword())) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        UUID personaId = personaService.getIdPersona(usuario.getId());
        String accessToken = jwtUtil.generateToken(username, personaId);


        return new LoginResponse(accessToken);
    }

}
