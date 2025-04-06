package com.aejimenez19.ControlCredito.service;

import com.aejimenez19.ControlCredito.model.Persona;
import com.aejimenez19.ControlCredito.repository.PersonaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.service.spi.ServiceException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PersonaService {

    private final PersonaRepository personaRepository;

    /**
     * Retrieves all clients associated with a specific prestador (lender).
     *
     * @param prestadorId The unique identifier (UUID) of the prestador/lender
     * @return List<Persona> A list of all clients (Persona objects) associated with the specified prestador.
     *         Returns an empty list if no clients are found.
     *
     * @throws IllegalArgumentException if prestadorId is null
     * @see Persona
     * @see PersonaRepository#findClientesByPrestadorId
     *
     * @apiNote This method fetches clients directly from the repository layer using
     *          the prestador's UUID as a filter criteria. The returned list includes
     *          all persona records that are marked as clients of the specified prestador.
     */
    public List<Persona> getClientsFromTheProvider(UUID prestadorId) {
        // Validate input
        if (prestadorId == null) {
            throw new IllegalArgumentException("Prestador ID cannot be null");
        }

        try {
            List<Persona> clients = personaRepository.findClientesByPrestadorId(prestadorId);
            return clients;
        } catch (Exception e) {
            throw new ServiceException("Failed to retrieve clients", e);
        }
    }
}
