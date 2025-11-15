package com.lab11.controllers;

import com.lab11.domain.entities.Alumno;
import com.lab11.services.AlumnoService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import java.util.HashMap;
import java.util.List;
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
    public String listar(@RequestParam(value = "format", required = false) String format,
                         Model model, Authentication authentication, HttpServletRequest request) {

        if (authentication != null) {
            model.addAttribute("titulo", "Hola usuario ".concat(authentication.getName()));
        }

        model.addAttribute("alumnos", alumnoService.listar());

        // Manejar exportación a PDF
        if ("pdf".equalsIgnoreCase(format)) {
            return "listar.pdf";
        }

        // Manejar exportación a Excel
        if ("xls".equalsIgnoreCase(format) || "xlsx".equalsIgnoreCase(format)) {
            return "listar.xls";
        }

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

    // REST API Endpoints para AJAX
    @Secured("ROLE_ADMIN")
    @GetMapping("/api/alumnos")
    @ResponseBody
    public ResponseEntity<List<Alumno>> listarAlumnos() {
        List<Alumno> alumnos = alumnoService.listar();
        return ResponseEntity.ok(alumnos);
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/api/alumnos/{id}")
    @ResponseBody
    public ResponseEntity<?> obtenerAlumno(@PathVariable Integer id) {
        Alumno alumno = alumnoService.buscar(id);
        if (alumno == null) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "El alumno no existe");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
        return ResponseEntity.ok(alumno);
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/api/alumnos")
    @ResponseBody
    public ResponseEntity<?> crearAlumno(@Valid @RequestBody Alumno alumno, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, Object> errors = new HashMap<>();
            result.getFieldErrors().forEach(err ->
                errors.put(err.getField(), err.getDefaultMessage())
            );
            return ResponseEntity.badRequest().body(errors);
        }

        alumnoService.grabar(alumno);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Alumno creado con éxito");
        response.put("alumno", alumno);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Secured("ROLE_ADMIN")
    @PutMapping("/api/alumnos/{id}")
    @ResponseBody
    public ResponseEntity<?> actualizarAlumno(@PathVariable Integer id,
                                              @Valid @RequestBody Alumno alumno,
                                              BindingResult result) {
        if (result.hasErrors()) {
            Map<String, Object> errors = new HashMap<>();
            result.getFieldErrors().forEach(err ->
                errors.put(err.getField(), err.getDefaultMessage())
            );
            return ResponseEntity.badRequest().body(errors);
        }

        Alumno alumnoActual = alumnoService.buscar(id);
        if (alumnoActual == null) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "El alumno no existe");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }

        alumnoActual.setNombre(alumno.getNombre());
        alumnoActual.setCreditos(alumno.getCreditos());
        alumnoService.grabar(alumnoActual);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Alumno actualizado con éxito");
        response.put("alumno", alumnoActual);
        return ResponseEntity.ok(response);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/api/alumnos/{id}")
    @ResponseBody
    public ResponseEntity<?> eliminarAlumno(@PathVariable Integer id) {
        Alumno alumno = alumnoService.buscar(id);
        if (alumno == null) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "El alumno no existe");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }

        alumnoService.eliminar(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Alumno eliminado con éxito");
        return ResponseEntity.ok(response);
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
