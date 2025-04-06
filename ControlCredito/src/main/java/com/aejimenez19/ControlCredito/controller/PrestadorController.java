package com.aejimenez19.ControlCredito.controller;


import com.aejimenez19.ControlCredito.dto.PrestamoResumenDto;
import com.aejimenez19.ControlCredito.model.Pago;
import com.aejimenez19.ControlCredito.model.Persona;
import com.aejimenez19.ControlCredito.model.Prestamo;
import com.aejimenez19.ControlCredito.service.PersonaService;
import com.aejimenez19.ControlCredito.service.PrestamoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/prestador")
@RequiredArgsConstructor
public class PrestadorController {

    private final PersonaService personaService;
    private final PrestamoService prestamoService;

    /**
     * Retrieves a list of clients (Persona) associated with a specific prestador (lender).
     *
     * @endpoint GET /clientes
     * @security Requires X-User-Id header with the prestador's UUID
     *
     * @param lenderId UUID of the prestador/lender making the request (passed via X-User-Id header)
     * @return List<Persona> A list of all clients (Persona objects) associated with the specified prestador
     *
     * @apiNote This endpoint allows prestadores to view all their associated clients
     * @see Persona
     * @see PersonaService#getClientsFromTheProvider
     */
    @GetMapping("/clientes")
    public List<Persona> getclient(@RequestHeader("X-User-Id") UUID lenderId) {
        return personaService.getClientsFromTheProvider(lenderId);
    }

    /**
     * Retrieves all loans associated with a specific client.
     *
     * @endpoint GET /clientes/{clienteId}/prestamos
     *
     * @param lenderId The UUID of the lender making the request, provided in X-User-Id header
     * @param clienteId The UUID of the client whose loans are being requested
     *
     * @return List<Prestamo> A list of loan objects associated with the specified client
     *
     * @security Requires X-User-Id header for lender authentication
     *
     */
    @GetMapping("/clientes/{clienteId}/prestamos")
    public List<PrestamoResumenDto> getLoansByClient(
            @RequestHeader("X-User-Id") UUID lenderId,
            @PathVariable UUID clienteId) {
        return prestamoService.getLoandsByAClient(lenderId, clienteId);
    }

    // Obtener el detalle de un prestamo
    @GetMapping("/prestamos/{prestamoId}/pagos")
    public List<Pago> getLoanPayments(@PathVariable UUID prestamoId) {
        return null;
    }

    // crear un prestamo asociandolo a cliente
    @PostMapping("/clientes/{clienteId}/prestamos")
    public Prestamo createLoans(@PathVariable UUID clienteId, @RequestBody Prestamo prestamo) {
        return null;
    }

    //guardar un pago de un prestamo
    @PostMapping("/prestamos/{prestamoId}/pagos")
    public Pago CreatePayment(@PathVariable UUID prestamoId, @RequestBody Pago pago) {
        return null;
    }

    //Agregar un nuevo cliente prestadores


}

