package com.eva3.evalu_03.controller;

import com.eva3.evalu_03.dto.JwtResponse;
import com.eva3.evalu_03.dto.LoginRequest;
import com.eva3.evalu_03.dto.MessageResponse;
import com.eva3.evalu_03.dto.RegistroRequest;
import com.eva3.evalu_03.entity.Rol;
import com.eva3.evalu_03.entity.Usuario;
import com.eva3.evalu_03.repository.RolRepository;
import com.eva3.evalu_03.repository.UsuarioRepository;
import com.eva3.evalu_03.security.JwtUtils;
import com.eva3.evalu_03.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticación", description = "Endpoints de autenticación y registro")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    @PostMapping("/login")
    @Operation(summary = "Iniciar sesión", description = "Autenticar usuario y obtener token JWT")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getNombreUsuario(),
                        loginRequest.getContrasena()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generarJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername(), roles));
    }

    @PostMapping("/registro")
    @Operation(summary = "Registrar usuario", description = "Registrar un nuevo usuario en el sistema")
    public ResponseEntity<?> registro(@Valid @RequestBody RegistroRequest registroRequest) {
        if (usuarioRepository.existsByNombreUsuario(registroRequest.getNombreUsuario())) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Error: El nombre de usuario ya está en uso"));
        }

        Usuario usuario = new Usuario();
        usuario.setNombreUsuario(registroRequest.getNombreUsuario());
        usuario.setContrasena(passwordEncoder.encode(registroRequest.getContrasena()));

        Set<String> strRoles = registroRequest.getRoles();
        Set<Rol> roles = new HashSet<>();

        if (strRoles == null || strRoles.isEmpty()) {
            Rol rolMozo = rolRepository.findByNombre("ROLE_MOZO")
                    .orElseThrow(() -> new RuntimeException("Error: Rol no encontrado"));
            roles.add(rolMozo);
        } else {
            strRoles.forEach(role -> {
                Rol rol = rolRepository.findByNombre(role)
                        .orElseThrow(() -> new RuntimeException("Error: Rol " + role + " no encontrado"));
                roles.add(rol);
            });
        }

        usuario.setRoles(roles);
        usuarioRepository.save(usuario);

        return ResponseEntity.ok(new MessageResponse("Usuario registrado exitosamente"));
    }
}
