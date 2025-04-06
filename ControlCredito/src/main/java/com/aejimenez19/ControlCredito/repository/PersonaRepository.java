package com.aejimenez19.ControlCredito.repository;

import com.aejimenez19.ControlCredito.model.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface PersonaRepository extends JpaRepository<Persona, UUID> {

    @Query("SELECT pc.cliente FROM PrestadorCliente pc WHERE pc.prestador.id = :prestadorId")
    List<Persona> findClientesByPrestadorId(UUID prestadorId);
}
