package com.aejimenez19.ControlCredito.service;

import com.aejimenez19.ControlCredito.constant.ConstantExpetion;
import com.aejimenez19.ControlCredito.model.Cliente;
import com.aejimenez19.ControlCredito.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteService {
    private final ClienteRepository clienteRepository;;

    /**
     * Obtiene todos los clientes.
     * @return Lista de clientes.
     */
    public List<Cliente> getClientes() {
        List<Cliente> list = clienteRepository.findAll();
        return list;
    }

    /**
     * Busca un cliente por su ID.
     * @param id ID del cliente.
     * @return Cliente encontrado o una excepción si no existe.
     */
    public Cliente getClienteById(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(ConstantExpetion.CLIENTE_NOT_FOUND_WITH_ID + id));
    }

    /**
     * Guarda un nuevo cliente.
     * @param cliente Datos del nuevo cliente.
     * @return Cliente guardado.
     */
    public Cliente saveCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }
}
