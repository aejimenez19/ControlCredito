package com.aejimenez19.auth_service.dto;

import lombok.Data;

@Data
public class LoginResponse {
    private String accessToken;

    public LoginResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}
