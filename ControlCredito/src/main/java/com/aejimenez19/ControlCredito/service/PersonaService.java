package com.aejimenez19.ControlCredito.service;

import com.aejimenez19.ControlCredito.constant.ConstantExpetion;
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
     * @param lenderId The unique identifier (UUID) of the prestador/lender
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
    public List<Persona> getClientsFromTheProvider(UUID lenderId) {

        if (lenderId == null) {
            throw new IllegalArgumentException(ConstantExpetion.NOT_NULL_LENDER_ID);
        }

        try {
            return personaRepository.findClientesByPrestadorId(lenderId);
        } catch (Exception e) {
            throw new ServiceException(ConstantExpetion.FAIL_RETRIEVER_CLIENTS, e);
        }
    }
}
