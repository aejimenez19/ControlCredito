package com.aejimenez19.auth_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @Column(name = "usuario_id")
    private UUID usuarioId;

    @Column(name = "creado_en")
    private Instant creadoEn;
}
