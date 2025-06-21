package com.aejimenez19.auth_service.repository;

import com.aejimenez19.auth_service.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;


public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {


    Optional<Usuario> findByUsuario(String username);
}
