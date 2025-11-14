package com.lab11.aop;

import com.lab11.domain.entities.Auditoria;
import com.lab11.domain.persistence.AuditoriaDao;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogginAspecto {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AuditoriaDao auditoriaDao;

    @Pointcut("execution(* com.lab11.services.AlumnoServiceImpl.*(..))")
    public void logPointCut() {
    }

    @After("logPointCut()")
    public void log(JoinPoint joinPoint) {
        logger.info("Llamando el metodo: " + joinPoint.getSignature().getName());

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String usuario = auth != null ? auth.getName() : "anonimo";

        Auditoria auditoria = new Auditoria(joinPoint.getSignature().getName(), usuario);
        auditoriaDao.save(auditoria);

        logger.info("Auditoria guardada: metodo=" + auditoria.getNombreMetodo() +
                   ", usuario=" + auditoria.getUsuario() +
                   ", fecha=" + auditoria.getFecha());
    }
}
