package com.eva3.evalu_03.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
public class RegistroRequest {
    @NotBlank
    @Size(min = 3, max = 50)
    private String nombreUsuario;

    @NotBlank
    @Size(min = 6, max = 40)
    private String contrasena;

    @NotEmpty
    private Set<String> roles;
}
