package com.aejimenez19.ControlCredito.controller;

import com.aejimenez19.ControlCredito.model.Pago;
import com.aejimenez19.ControlCredito.model.Prestamo;
import com.aejimenez19.ControlCredito.service.PagoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<List<Pago>> getPagoByPrestamo(@PathVariable Long id) {
        Prestamo prestamo = new Prestamo();
        prestamo.setIdPrestamo(id);
        return ResponseEntity.ok(pagoService.getPagoByPrestamo(prestamo));
    }

    /**
     * Saves a payment (Pago) to the repository and returns the saved payment.
     *
     * @param pago the payment to be saved
     * @return a ResponseEntity containing the saved payment
     */
    @PostMapping()
    public ResponseEntity<Pago> savePago(@RequestBody Pago pago) {
        Pago savedPago = pagoService.savePago(pago);

        return ResponseEntity.ok(savedPago);
    }


}
