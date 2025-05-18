package com.aejimenez19.ControlCredito.controller;


import com.aejimenez19.ControlCredito.dto.PrestamoResumenDto;
import com.aejimenez19.ControlCredito.model.Pago;
import com.aejimenez19.ControlCredito.model.Persona;
import com.aejimenez19.ControlCredito.model.Prestamo;
import com.aejimenez19.ControlCredito.service.PagoService;
import com.aejimenez19.ControlCredito.service.PersonaService;
import com.aejimenez19.ControlCredito.service.PrestamoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/prestador")
@RequiredArgsConstructor
public class PrestadorController {

    private final PersonaService personaService;
    private final PrestamoService prestamoService;
    private final PagoService pagoService;

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
    public ResponseEntity<List<Persona>> getclient(@RequestHeader("X-User-Id") UUID lenderId) {
        return ResponseEntity
                .ok()
                .body(personaService.getClientsFromTheProvider(lenderId));
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
    public ResponseEntity<List<PrestamoResumenDto>> getLoansByClient(
            @RequestHeader("X-User-Id") UUID lenderId,
            @PathVariable UUID clienteId) {
        return ResponseEntity
                .ok()
                .body(prestamoService.getLoandsByAClient(lenderId, clienteId));
    }

    /**
     * Retrieves detailed information about a specific loan/prestamo.
     *
     * @param lenderId   The UUID of the lender making the request, provided through X-User-Id header
     * @param prestamoId The UUID of the loan/prestamo to retrieve, provided through path variable
     * @return Prestamo object containing the loan details
     */
    @GetMapping("/prestamos/{prestamoId}/detalle")
    public ResponseEntity<Prestamo> getLoanPayments(@RequestHeader("X-User-Id") UUID lenderId,
                                      @PathVariable UUID prestamoId) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(prestamoService.findPrestamoById(lenderId, prestamoId));
    }


    @PostMapping("newPrestamo/{clienteId}")
    public ResponseEntity<Prestamo> createLoans(@RequestHeader("X-User-Id") UUID lenderId,
                                                @PathVariable UUID clienteId,
                                                @RequestBody Prestamo prestamo) throws Exception {
        return ResponseEntity.ok().body(prestamoService.savePrestamo(lenderId,clienteId, prestamo));
    }

    /**
     * Saves a payment (Pago) to the repository and returns the saved payment.
     *
     * @param pago the payment to be saved
     * @return a ResponseEntity containing the saved payment
     */
    @PostMapping("pago/newPago")
    public ResponseEntity<Pago> savePago(@RequestBody Pago pago) {
        Pago savedPago = pagoService.savePago(pago);
        return ResponseEntity.ok(savedPago);
    }




}

