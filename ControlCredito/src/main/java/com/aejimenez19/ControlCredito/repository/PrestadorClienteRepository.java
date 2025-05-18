package com.aejimenez19.ControlCredito.repository;

import com.aejimenez19.ControlCredito.model.PrestadorCliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface PrestadorClienteRepository extends JpaRepository<PrestadorCliente, UUID> {
    PrestadorCliente findByPrestadorIdAndClienteId(UUID prestadorId, UUID clienteId);
}
