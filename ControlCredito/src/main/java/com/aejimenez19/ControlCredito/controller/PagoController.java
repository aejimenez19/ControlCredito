package com.aejimenez19.ControlCredito.controller;

import com.aejimenez19.ControlCredito.model.Pago;
import com.aejimenez19.ControlCredito.model.Prestamo;
import com.aejimenez19.ControlCredito.service.PagoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/pago")
@RequiredArgsConstructor
public class PagoController {
    private final PagoService pagoService;


    /**
     * Retrieves a list of payments (Pagos) associated with a given loan (Prestamo).
     *
     * @param id the ID of the loan whose payments are to be retrieved
     * @return a ResponseEntity containing a list of payments associated with the specified loan
     */
    @RequestMapping("{id}")
    public ResponseEntity<List<Pago>> getPagoByPrestamo(@PathVariable UUID id) {
        Prestamo prestamo = new Prestamo();
        prestamo.setId(id);
        return ResponseEntity.ok(pagoService.getPagoByPrestamo(prestamo));
    }




}
