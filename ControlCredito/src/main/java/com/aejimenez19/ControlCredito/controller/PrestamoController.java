package com.aejimenez19.ControlCredito.controller;

import com.aejimenez19.ControlCredito.model.Cliente;
import com.aejimenez19.ControlCredito.model.Prestamo;
import com.aejimenez19.ControlCredito.service.PrestamoService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prestamo")
@RequiredArgsConstructor
public class PrestamoController {
    private final PrestamoService prestamoService;

    @GetMapping("/{id}")
    public ResponseEntity<List<Prestamo>> getPrestamosByClienteId(@PathVariable Long id) {
        Cliente cliente = new Cliente();
        cliente.setId(id);
        return ResponseEntity.ok(prestamoService.getPrestamosByIdCliente(cliente));
    }

    @PostMapping("/save")
    public ResponseEntity<Prestamo> savePrestamo(@RequestBody Prestamo prestamo) {
        Prestamo savedPrestamo = prestamoService.savePrestamo(prestamo);
        return ResponseEntity.ok(savedPrestamo);
    }
}
