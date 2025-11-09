package com.lab9.controladores;

import com.lab9.modelo.documents.Alumno;
import com.lab9.servicios.AlumnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;

@Controller
@SessionAttributes("alumno")
public class AlumnoController {

    @Autowired
    private AlumnoService servicio;

    @RequestMapping(value = "/listar", method = RequestMethod.GET)
    public String listar(Model model) {
        model.addAttribute("titulo", "Listado de Alumnos CJAVA");
        model.addAttribute("alumnos", servicio.listar());
        return "listarView";
    }

    @RequestMapping(value = "/form")
    public String crear(Model model) {
        Alumno alumno = new Alumno();
        model.addAttribute("alumno", alumno);
        model.addAttribute("titulo", "Formulario de Alumno");
        return "formView";
    }

    @RequestMapping(value = "/form/{id}")
    public String editar(@PathVariable(value = "id") String id, Model model) {
        Alumno alumno = null;
        if (id.length() > 0) {
            alumno = servicio.buscar(id);
        } else {
            return "redirect:/listar";
        }
        model.addAttribute("alumno", alumno);
        model.addAttribute("titulo", "Editar Alumno");
        return "formView";
    }

    @RequestMapping(value = "/form", method = RequestMethod.POST)
    public String guardar(@Valid Alumno alumno, BindingResult result, Model model, SessionStatus status) {
        if (result.hasErrors()) {
            model.addAttribute("titulo", "Formulario de Alumno");
            return "formView";
        }
        servicio.grabar(alumno);
        status.setComplete();
        return "redirect:/listar";
    }

    @RequestMapping(value = "/eliminar/{id}")
    public String eliminar(@PathVariable(value = "id") String id) {
        if (id.length() > 0) {
            servicio.eliminar(id);
        }
        return "redirect:/listar";
    }
}
