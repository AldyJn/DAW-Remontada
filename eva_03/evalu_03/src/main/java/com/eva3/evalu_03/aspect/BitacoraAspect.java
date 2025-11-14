package com.eva3.evalu_03.aspect;

import com.eva3.evalu_03.entity.Bitacora;
import com.eva3.evalu_03.repository.BitacoraRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;
import java.util.Arrays;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class BitacoraAspect {

    private final BitacoraRepository bitacoraRepository;

    @AfterReturning(value = "@annotation(auditado)", returning = "result")
    public void registrarAccionExitosa(JoinPoint joinPoint, Auditado auditado, Object result) {
        try {
            registrarEnBitacora(joinPoint, auditado, "EXITOSA", null);
        } catch (Exception e) {
            log.error("Error al registrar en bitácora: {}", e.getMessage());
        }
    }

    @AfterThrowing(value = "@annotation(auditado)", throwing = "error")
    public void registrarAccionFallida(JoinPoint joinPoint, Auditado auditado, Throwable error) {
        try {
            registrarEnBitacora(joinPoint, auditado, "FALLIDA", error.getMessage());
        } catch (Exception e) {
            log.error("Error al registrar en bitácora: {}", e.getMessage());
        }
    }

    private void registrarEnBitacora(JoinPoint joinPoint, Auditado auditado, String resultado, String mensajeError) {
        Bitacora bitacora = new Bitacora();

        // Obtener usuario autenticado
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String usuario = (auth != null && auth.isAuthenticated()) ? auth.getName() : "ANONIMO";
        bitacora.setUsuario(usuario);

        // Información del método
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String metodo = signature.getMethod().getName();
        String clase = signature.getDeclaringType().getSimpleName();
        bitacora.setMetodo(clase + "." + metodo);

        // Acción y descripción
        String accion = auditado.accion().isEmpty() ? metodo.toUpperCase() : auditado.accion();
        bitacora.setAccion(accion + "_" + resultado);

        String descripcion = auditado.descripcion();
        if (mensajeError != null) {
            descripcion += " - Error: " + mensajeError;
        }
        bitacora.setDescripcion(descripcion);

        // Fecha
        bitacora.setFecha(LocalDateTime.now());

        // IP del cliente
        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes != null) {
                HttpServletRequest request = attributes.getRequest();
                String ip = request.getHeader("X-Forwarded-For");
                if (ip == null || ip.isEmpty()) {
                    ip = request.getRemoteAddr();
                }
                bitacora.setIpCliente(ip);
            }
        } catch (Exception e) {
            log.debug("No se pudo obtener IP del cliente: {}", e.getMessage());
        }

        // Guardar en base de datos
        bitacoraRepository.save(bitacora);
        log.info("Bitácora registrada: {} - {} - {}", usuario, accion, resultado);
    }
}
