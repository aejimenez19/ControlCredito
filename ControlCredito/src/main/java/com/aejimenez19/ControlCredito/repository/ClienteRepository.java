package com.aejimenez19.ControlCredito.repository;

import com.aejimenez19.ControlCredito.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
