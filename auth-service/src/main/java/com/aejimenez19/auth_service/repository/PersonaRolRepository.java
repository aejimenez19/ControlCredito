package com.aejimenez19.auth_service.repository;

import com.aejimenez19.auth_service.entity.PersonaRol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;


public interface PersonaRolRepository extends JpaRepository<PersonaRol, UUID> {
}
