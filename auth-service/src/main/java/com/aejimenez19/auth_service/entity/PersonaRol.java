package com.aejimenez19.auth_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "persona_rol", uniqueConstraints = @UniqueConstraint(columnNames = {"persona_id", "tipo_id"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonaRol {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "persona_id", nullable = false)
    private Persona persona;

    @ManyToOne
    @JoinColumn(name = "tipo_id", nullable = false)
    private TipoPersona tipoPersona;
}
