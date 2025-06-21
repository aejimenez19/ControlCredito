package com.aejimenez19.auth_service.repository;


import com.aejimenez19.auth_service.entity.Persona;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;


public interface PersonaReporsitory extends JpaRepository<Persona, UUID> {

    Optional<Persona> findByUsuarioId(UUID usuarioID);

    Optional<Persona> findByIdentificacion(String identification);
}
