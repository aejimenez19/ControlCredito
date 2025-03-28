package com.aejimenez19.ControlCredito.repository;

import com.aejimenez19.ControlCredito.model.Cliente;
import com.aejimenez19.ControlCredito.model.Prestamo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {
    List<Prestamo> findByCliente(Cliente cliente);

    @Query(value = "SELECT * FROM prestamos p WHERE fecha_vencimiento < CURRENT_DATE", nativeQuery = true)
    List<Prestamo> findOverdueLoans(Pageable pegeable);
}
