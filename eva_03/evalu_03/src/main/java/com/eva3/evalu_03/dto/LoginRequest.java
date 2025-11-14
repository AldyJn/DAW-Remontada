package com.eva3.evalu_03.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {
    @NotBlank
    private String nombreUsuario;

    @NotBlank
    private String contrasena;
}
