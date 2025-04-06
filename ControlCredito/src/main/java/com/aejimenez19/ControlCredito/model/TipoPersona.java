package com.aejimenez19.ControlCredito.model;

import lombok.*;
import jakarta.persistence.*;

@Entity
@Table(name = "tipo_persona")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TipoPersona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String nombre;
}

