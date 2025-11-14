package com.lab11.controllers;

import com.lab11.domain.entities.Alumno;
import com.lab11.services.AlumnoService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collection;
import java.util.Map;

@Controller
@SessionAttributes("alumno")
public class AlumnoController {

    @Autowired
    private AlumnoService alumnoService;

    @GetMapping(value = "/")
    public String inicio(Model model, Authentication authentication, HttpServletRequest request,
                          SecurityContextHolderAwareRequestWrapper requestWrapper) {

        if (authentication != null) {
            model.addAttribute("titulo", "Bienvenido ".concat(authentication.getName()));
        }

        return "inicio";
    }

    @GetMapping("/login")
    public String login(Model model,
                         @RequestParam(value = "error", required = false) String error,
                         @RequestParam(value = "logout", required = false) String logout) {

        if (error != null) {
            model.addAttribute("error", "Error en el login: Nombre de usuario o contraseña incorrecta, por favor vuelva a intentarlo!");
        }

        if (logout != null) {
            model.addAttribute("success", "Ha cerrado sesion con exito!");
        }

        return "login";
    }

    @RequestMapping(value = "/listar", method = RequestMethod.GET)
    public String listar(Model model, Authentication authentication, HttpServletRequest request) {

        if (authentication != null) {
            model.addAttribute("titulo", "Hola usuario ".concat(authentication.getName()));
        }

        model.addAttribute("alumnos", alumnoService.listar());
        return "listar";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/form")
    public String crear(Model model) {
        Alumno alumno = new Alumno();
        model.addAttribute("alumno", alumno);
        model.addAttribute("titulo", "Formulario de Alumno");
        return "form";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/form/{id}")
    public String editar(@PathVariable(value = "id") Integer id, Model model, RedirectAttributes flash) {
        Alumno alumno = null;

        if (id > 0) {
            alumno = alumnoService.buscar(id);
            if (alumno == null) {
                flash.addFlashAttribute("error", "El ID del alumno no existe en la BBDD!");
                return "redirect:/listar";
            }
        } else {
            flash.addFlashAttribute("error", "El ID del alumno no puede ser cero!");
            return "redirect:/listar";
        }

        model.addAttribute("alumno", alumno);
        model.addAttribute("titulo", "Editar Alumno");
        return "form";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/form", method = RequestMethod.POST)
    public String guardar(@Valid Alumno alumno, BindingResult result, Model model,
                           RedirectAttributes flash, SessionStatus status) {

        if (result.hasErrors()) {
            model.addAttribute("titulo", "Formulario de Alumno");
            return "form";
        }

        String mensajeFlash = (alumno.getId() != null && alumno.getId() != 0) ? "Alumno editado con exito!" : "Alumno creado con exito!";

        alumnoService.grabar(alumno);
        status.setComplete();
        flash.addFlashAttribute("success", mensajeFlash);
        return "redirect:/listar";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/eliminar/{id}")
    public String eliminar(@PathVariable(value = "id") Integer id, RedirectAttributes flash) {

        if (id > 0) {
            alumnoService.eliminar(id);
            flash.addFlashAttribute("success", "Alumno eliminado con exito!");
        }

        return "redirect:/listar";
    }

    @Secured("ROLE_USER")
    @GetMapping(value = "/ver/{id}")
    public String ver(@PathVariable(value = "id") Integer id, Map<String, Object> model, RedirectAttributes flash) {
        Alumno alumno = alumnoService.buscar(id);
        if (alumno == null) {
            flash.addFlashAttribute("error", "El alumno no existe en la base de datos");
            return "redirect:/listar";
        }

        model.put("alumno", alumno);
        model.put("titulo", "Detalle alumno: " + alumno.getNombre());
        return "ver";
    }

    private boolean hasRole(String role) {
        SecurityContext context = SecurityContextHolder.getContext();

        if (context == null) {
            return false;
        }

        Authentication auth = context.getAuthentication();

        if (auth == null) {
            return false;
        }

        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();

        return authorities.contains(new SimpleGrantedAuthority(role));
    }
}
