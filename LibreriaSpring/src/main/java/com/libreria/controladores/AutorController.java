package com.libreria.controladores;

import com.libreria.entidades.Autor;
import com.libreria.errores.ErrorServicio;
import com.libreria.servicios.AutorServicio;
import java.util.logging.Level;
import java.util.logging.Logger;
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
@RequestMapping("/autor")
public class AutorController {

    @Autowired
    private AutorServicio autorServicio;

    @PostMapping("/crear")
    public String crear(ModelMap modelo, @RequestParam String nombre, @RequestParam MultipartFile archivo) {
        try {
            autorServicio.crear(nombre, archivo);
        } catch (ErrorServicio e) {
            modelo.put("error", e.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("archivo", archivo);
            return "registro.html";
        }
        modelo.put("titulo", "El autor '" + nombre + "' fue cargado con exito!");
        return "redirect:/index";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable String id, ModelMap modelo) {

        try {
            Autor autor = autorServicio.buscarPorId(id);
            modelo.put("autor", autor);
        } catch (Exception e) {
        }

        return "/editarAutor.html";
    }

    @PostMapping
    public String editar(ModelMap modelo, @RequestParam String id, @RequestParam String nombre, @RequestParam MultipartFile archivo) {
        try {
            autorServicio.editar(id, nombre, archivo);
        } catch (ErrorServicio e) {
            modelo.put("error", e.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("archivo", archivo);
            return "registro.html";
        }
        modelo.put("exito", "Se edito el autor '" + nombre + "' con exito!");
        return "/index.html";
    }

}


