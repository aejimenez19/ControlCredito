package com.aejimenez19.auth_service.service;

import com.aejimenez19.auth_service.configuration.JwtUtil;
import com.aejimenez19.auth_service.dto.LoginResponse;
import com.aejimenez19.auth_service.dto.RegisterRequest;
import com.aejimenez19.auth_service.entity.Persona;
import com.aejimenez19.auth_service.entity.PersonaRol;
import com.aejimenez19.auth_service.entity.TipoPersona;
import com.aejimenez19.auth_service.entity.Usuario;
import com.aejimenez19.auth_service.repository.PersonaReporsitory;
import com.aejimenez19.auth_service.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PersonaService personaService;
    private final PersonaRolService personaRolService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public Usuario register(RegisterRequest request) {
        if (usuarioRepository.findByUsuario(request.getUser()).isPresent()) {
            throw new RuntimeException("El usuario ya existe");
        }

        if (personaService.getPersonaByIdentification(request.getIdentification()).isPresent()){
            throw new RuntimeException("La persona ya se encuentra registrada");
        }

        Usuario nuevo = builUsuario(request);
        Usuario usuario = usuarioRepository.save(nuevo);
        Persona newPersona = builPersona(request, usuario);
        Persona savePersona = personaService.savePersona(newPersona);
        PersonaRol newPersonaRol = builPersonaRol(savePersona, request.getRol());
        PersonaRol personaRol = personaRolService.savePersonaRol(newPersonaRol);
        return usuario;
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

    private Usuario builUsuario(RegisterRequest request){
        return Usuario.builder()
                .estado("Activo")
                .usuario(request.getUser())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
    }

    private Persona builPersona(RegisterRequest request, Usuario usuario){
        return Persona.builder()
                .nombre(request.getName())
                .telefono(request.getPhone())
                .identificacion(request.getIdentification())
                .creadoEn(Instant.now())
                .usuarioId(usuario.getId())
                .build();
    }

    private PersonaRol builPersonaRol(Persona persona, int rol){
        return PersonaRol.builder()
                .persona(persona)
                .tipoPersona(TipoPersona.builder().id(rol).build())
                .build();
    }

}
