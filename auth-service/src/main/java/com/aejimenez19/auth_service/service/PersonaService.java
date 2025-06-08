package com.aejimenez19.auth_service.service;

import com.aejimenez19.auth_service.entity.Persona;
import com.aejimenez19.auth_service.repository.PersonaReporsitory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PersonaService {
    private final PersonaReporsitory personaReporsitory;


    public UUID getIdPersona(UUID usuarioID) {
        Persona persona = personaReporsitory.findByUsuarioId(usuarioID)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return persona.getId();
    }
}
