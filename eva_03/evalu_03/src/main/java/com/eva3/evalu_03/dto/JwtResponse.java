package com.eva3.evalu_03.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponse {
    private String token;
    private String tipo = "Bearer";
    private String nombreUsuario;
    private List<String> roles;

    public JwtResponse(String token, String nombreUsuario, List<String> roles) {
        this.token = token;
        this.nombreUsuario = nombreUsuario;
        this.roles = roles;
    }
}
