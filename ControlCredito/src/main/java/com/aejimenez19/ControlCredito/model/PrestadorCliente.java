package com.aejimenez19.ControlCredito.model;

import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "prestador_cliente", uniqueConstraints = @UniqueConstraint(columnNames = {"prestador_id", "cliente_id"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrestadorCliente {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "prestador_id", nullable = false)
    private Persona prestador;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Persona cliente;

    @Column(name = "fecha_asociacion")
    private LocalDate fechaAsociacion;
}

