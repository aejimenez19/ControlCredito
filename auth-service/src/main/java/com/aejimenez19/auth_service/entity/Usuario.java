package com.aejimenez19.auth_service.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
@Table(name = "usuario")
public class Usuario {
    @Id
    @GeneratedValue
    private UUID id;
    private String usuario;
    @Column(name = "password_hash")
    private String password;
    private String estado;
}
