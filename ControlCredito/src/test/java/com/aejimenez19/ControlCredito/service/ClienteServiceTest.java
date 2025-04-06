package com.aejimenez19.ControlCredito.service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class ClienteServiceTest {

    @MockitoBean
    private ClienteRepository clienteRepository;

    /**
     * Test case for getClientes method
     * Verifies that the method returns all clients from the repository
     */
    @Test
    public void test_getClientes_returnsAllClients() {
        // Arrange
        List<Cliente> expectedClients = Arrays.asList(
                new Cliente(1L, "Juan", "Pérez", "3001234567", "Calle Falsa 123", LocalDate.of(2023, 1, 15), null),
                new Cliente(2L, "Ana", "Gómez", "3017654321", "Avenida Siempre Viva 456", LocalDate.of(2023, 2, 10), null),
                new Cliente(3L, "Luis", "Martínez", "3029876543", "Carrera 7 #89-10", LocalDate.of(2023, 3, 5), null)
        );
        when(clienteRepository.findAll()).thenReturn(expectedClients);

        ClienteService clienteService = new ClienteService(clienteRepository);

        // Act
        List<Cliente> actualClients = clienteService.getClientes();

        // Assert
        assertEquals(expectedClients, actualClients);
        Mockito.verify(clienteRepository).findAll();
    }


    /**
     * Test case for getClienteById method
     * Verifies that the method returns the correct client when it exists in the repository
     */
    @Test
    public void test_getClienteById_returnsExistingClient() {
        // Arrange
        Long clientId = 1L;
        Cliente expectedClient = new Cliente(clientId, "Juan", "Pérez", "3001234567", "Calle Falsa 123", LocalDate.of(2023, 1, 15), null);
        when(clienteRepository.findById(clientId)).thenReturn(Optional.of(expectedClient));

        ClienteService clienteService = new ClienteService(clienteRepository);

        // Act
        Cliente actualClient = clienteService.getClienteById(clientId);

        // Assert
        assertEquals(expectedClient, actualClient);
        Mockito.verify(clienteRepository).findById(clientId);
    }

    /**
     * Test case for getClienteById method when the client is not found
     * Verifies that the method throws a RuntimeException with the correct message
     * when a client with the given ID does not exist in the repository
     */
    @Test
    public void test_getClienteById_throwsExceptionWhenClientNotFound() {
        // Arrange
        Long nonExistentId = 999L;
        when(clienteRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        ClienteService clienteService = new ClienteService(clienteRepository);

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            clienteService.getClienteById(nonExistentId);
        });

        assertEquals("Cliente no encontrado con ID: " + nonExistentId, exception.getMessage());
        Mockito.verify(clienteRepository).findById(nonExistentId);
    }


}
