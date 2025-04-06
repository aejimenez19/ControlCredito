package com.aejimenez19.ControlCredito.model;

import lombok.*;
import jakarta.persistence.*;
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
