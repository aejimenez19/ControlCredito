package com.aejimenez19.ControlCredito.service;

import com.aejimenez19.ControlCredito.model.PrestadorCliente;
import com.aejimenez19.ControlCredito.repository.PrestadorClienteRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.service.spi.ServiceException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PrestadorClienteService {
    private final PrestadorClienteRepository prestadorClienteRepository;

public Optional<PrestadorCliente> findPrestadorCliente(UUID prestadorId, UUID clienteId) {

    if (prestadorId == null || clienteId == null) {
        throw new IllegalArgumentException("PrestadorId and ClienteId must not be null");
    }

    try {
        return Optional.ofNullable(
            prestadorClienteRepository.findByPrestadorIdAndClienteId(prestadorId, clienteId)
        );
    } catch (Exception e) {
        throw new ServiceException("Error retrieving PrestadorCliente", e);
    }
}

}
