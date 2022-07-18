package com.libreria.controladores;

import com.libreria.entidades.Editorial;
import com.libreria.errores.ErrorServicio;
import com.libreria.servicios.EditorialServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/editorial")
public class EditorialController {

    @Autowired
    private EditorialServicio editorialServicio;

    @PostMapping("/crear")
    public String crear(ModelMap modelo, @RequestParam String nombre) {
        try {
            editorialServicio.crear(nombre);
        } catch (ErrorServicio e) {
            modelo.put("errorReg", e.getMessage());
            modelo.put("nombre", nombre);
            return "registro.html";
        }
        modelo.put("titulo", "La editorial '" + nombre + "' fue cargada con exito!");
        return "redirect:/index";
    }

    @GetMapping("/editar/{id}")
    public String editar(ModelMap modelo, @RequestParam String id ) {

        try {
            Editorial editorial = editorialServicio.buscarPorId(id);
            modelo.put("editorial", editorial);
        } catch (Exception e) {
        }

        return "/editarAutor.html";
    }

    @PostMapping
    public String editar(ModelMap modelo, @RequestParam String id, @RequestParam String nombre) {
        try {
            editorialServicio.editar(id, nombre);
        } catch (ErrorServicio e) {
            modelo.put("error", e.getMessage());
            modelo.put("nombre", nombre);
            return "registro.html";
        }
        modelo.put("exito", "Se edito la editorial '" + nombre + "' con exito!");
        return "/index.html";
    }

}
