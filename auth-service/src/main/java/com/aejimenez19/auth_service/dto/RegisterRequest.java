package com.aejimenez19.auth_service.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String usuario;
    private String password;
}
