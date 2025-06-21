package com.aejimenez19.auth_service.service;

import com.aejimenez19.auth_service.entity.PersonaRol;
import com.aejimenez19.auth_service.repository.PersonaRolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonaRolService {
    private final PersonaRolRepository personaRolRepository;

    public PersonaRol savePersonaRol(PersonaRol newPersonaRol) {
        return personaRolRepository.save(newPersonaRol);
    }
}
