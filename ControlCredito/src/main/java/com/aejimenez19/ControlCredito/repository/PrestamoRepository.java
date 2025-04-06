package com.aejimenez19.ControlCredito.repository;

import com.aejimenez19.ControlCredito.model.Prestamo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PrestamoRepository extends JpaRepository<Prestamo, UUID> {
    //List<Prestamo> findByCliente(Cliente cliente);

    @Query("SELECT p FROM Prestamo p WHERE p.prestadorCliente.prestador.id = :prestadorId AND p.prestadorCliente.cliente.id = :clienteId")
    List<Prestamo> findByPrestadorIdAndClienteId(UUID prestadorId, UUID clienteId);

    @Query(value = "SELECT * FROM prestamos p WHERE fecha_vencimiento < CURRENT_DATE", nativeQuery = true)
    List<Prestamo> findOverdueLoans(Pageable pegeable);
}
