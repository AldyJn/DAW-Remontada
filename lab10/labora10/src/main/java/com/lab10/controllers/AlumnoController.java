package com.lab10.controllers;

import com.lab10.domain.entities.Alumno;
import com.lab10.services.AlumnoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@SessionAttributes("alumno")
public class AlumnoController {

    @Autowired
    private AlumnoService alumnoService;

    @RequestMapping(value = "/listar", method = RequestMethod.GET)
    public String listar(Model model) {
        model.addAttribute("alumnos", alumnoService.listar());
        model.addAttribute("titulo", "Lista de Alumnos");
        return "listar";
    }

    @RequestMapping(value = "/form")
    public String crear(Model model) {
        Alumno alumno = new Alumno();
        model.addAttribute("alumno", alumno);
        model.addAttribute("titulo", "Formulario de Alumno");
        return "form";
    }

    @RequestMapping(value = "/form/{id}")
    public String editar(@PathVariable(value = "id") Integer id, Model model) {
        Alumno alumno = null;
        if (id > 0) {
            alumno = alumnoService.buscar(id);
        } else {
            return "redirect:/listar";
        }
        model.addAttribute("alumno", alumno);
        model.addAttribute("titulo", "Editar Alumno");
        return "form";
    }

    @RequestMapping(value = "/form", method = RequestMethod.POST)
    public String guardar(@Valid Alumno alumno, BindingResult result, Model model, SessionStatus status) {
        if (result.hasErrors()) {
            model.addAttribute("titulo", "Formulario de Alumno");
            return "form";
        }
        alumnoService.grabar(alumno);
        status.setComplete();
        return "redirect:/listar";
    }

    @RequestMapping(value = "/eliminar/{id}")
    public String eliminar(@PathVariable(value = "id") Integer id) {
        if (id > 0) {
            alumnoService.eliminar(id);
        }
        return "redirect:/listar";
    }

    @RequestMapping(value = "/ver")
    public String ver(Model model) {
        model.addAttribute("alumnos", alumnoService.listar());
        model.addAttribute("titulo", "Lista de Alumnos");
        return "alumno/ver";
    }
}
