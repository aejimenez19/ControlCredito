package com.aejimenez19.ControlCredito.model;

import lombok.*;
import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "persona")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Persona {

    @Id
    @GeneratedValue
    private UUID id;

    private String nombre;

    @Column(unique = true)
    private String identificacion;

    private String telefono;

    @Column(name = "creado_en")
    private Instant creadoEn;
}
