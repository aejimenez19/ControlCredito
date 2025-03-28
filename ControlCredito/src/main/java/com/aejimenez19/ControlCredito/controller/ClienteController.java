package com.aejimenez19.ControlCredito.controller;

import com.aejimenez19.ControlCredito.model.Cliente;
import com.aejimenez19.ControlCredito.service.ClienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class ClienteController {
    private final ClienteService clienteService;


    /**
     * Retrieves all clients from the system.
     *
     * @return ResponseEntity containing a list of all clients
     * @since 1.0
     */
    @GetMapping
    public ResponseEntity<List<Cliente>> getClientes() {
        List<Cliente> clientes = clienteService.getClientes();
        return ResponseEntity.ok(clientes);
    }

    /**
     * Retrieves a client by their ID.
     *
     * @param id The ID of the client to retrieve
     * @return ResponseEntity containing the client with the specified ID
     * @since 1.0
     */
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> getClienteById(@PathVariable Long id) {
        Cliente cliente = clienteService.getClienteById(id);
        return ResponseEntity.ok(cliente);
    }

    /**
     * Saves a new client.
     *
     * @param cliente The client data to be saved
     * @return ResponseEntity containing the saved client
     * @since 1.0
     */
    @PostMapping("/save")
    public ResponseEntity<Cliente> saveCliente(@RequestBody Cliente cliente) {
        Cliente savedCliente = clienteService.saveCliente(cliente);
        return ResponseEntity.ok(savedCliente);
    }



}
