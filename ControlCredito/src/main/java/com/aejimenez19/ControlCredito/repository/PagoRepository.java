package com.aejimenez19.ControlCredito.repository;

import com.aejimenez19.ControlCredito.model.Pago;
import com.aejimenez19.ControlCredito.model.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Long> {
    List<Pago> findByPrestamo(Prestamo prestamo);
}
