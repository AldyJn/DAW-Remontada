package com.lab10.aspects;

import com.lab10.domain.entities.Alumno;
import com.lab10.domain.entities.Auditoria;
import com.lab10.domain.persistence.AuditoriaDao;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Calendar;

@Component
@Aspect
public class LogginAspecto {

    private Long tx;

    @Autowired
    private AuditoriaDao auditoriaDao;

    @Around("execution(* com.lab10.services.*ServiceImpl.*(..))")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        tx = System.currentTimeMillis();
        Long currTime = System.currentTimeMillis();
        Logger logger = LoggerFactory.getLogger(joinPoint.getTarget().getClass());
        String metodo = joinPoint.getSignature().getName();
        Object result = null;

        if(joinPoint.getArgs().length > 0)
            logger.info(metodo + "() INPUT:" + Arrays.toString(joinPoint.getArgs()));

        try {
            result = joinPoint.proceed();
        } catch (Throwable e) {
            logger.error(e.getMessage());
            throw e;
        }

        logger.info(metodo + "(): tiempo transcurrido " + (System.currentTimeMillis() - currTime) + " ms.");
        return result;
    }

    @After("execution(* com.lab10.controllers.*Controller.guardar*(..)) ||" +
           "execution(* com.lab10.controllers.*Controller.editar*(..)) ||" +
           "execution(* com.lab10.controllers.*Controller.eliminar*(..))")
    public void auditoria(JoinPoint joinPoint) throws Throwable {
        Logger logger = LoggerFactory.getLogger(joinPoint.getTarget().getClass());
        String metodo = joinPoint.getSignature().getName();
        Integer id = 0;

        if(metodo.startsWith("guardar")){
            Object[] parametros = joinPoint.getArgs();
            Alumno alumno = (Alumno)parametros[0];
            id = alumno.getId();
        }
        else if(metodo.startsWith("editar")){
            Object[] parametros = joinPoint.getArgs();
            id = (Integer)parametros[0];
        }
        else if(metodo.startsWith("eliminar")){
            Object[] parametros = joinPoint.getArgs();
            id = (Integer)parametros[0];
        }

        String traza = "tx[" + tx + "] - " + metodo;
        logger.info(traza + "(): registrando auditoria...");
        auditoriaDao.save(new Auditoria("alumnos", id, Calendar.getInstance().getTime(),
                "usuario", metodo));
    }
}
