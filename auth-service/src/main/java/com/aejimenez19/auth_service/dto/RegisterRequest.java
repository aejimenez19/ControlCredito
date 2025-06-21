package com.aejimenez19.auth_service.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String user;
    private String password;
    private String name;
    private String phone;
    private String identification;
    private int rol;
}
