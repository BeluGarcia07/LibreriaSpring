package com.libreria.controladores;

import com.libreria.entidades.Autor;
import com.libreria.entidades.Editorial;
import com.libreria.entidades.Libro;
import com.libreria.enumeracion.Categoria;
import com.libreria.errores.ErrorServicio;
import com.libreria.repositorios.AutorRepositorio;
import com.libreria.repositorios.EditorialRepositorio;
import com.libreria.servicios.LibroServicio;
import com.libreria.servicios.UsuarioServicio;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class PortalControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;
    @Autowired
    private LibroServicio libroServicio;
    @Autowired
    private AutorRepositorio autorRepositorio;
    @Autowired
    private EditorialRepositorio editorialRepositorio;

    @GetMapping("/")
    public String index() {
        return "index.html";
    }

    @GetMapping("/autor")
    public String autor() {
        return "autor.html";
    }

    @GetMapping("/libro")
    public String libro(ModelMap modelo, @RequestParam(required = false) String categoria)throws ErrorServicio{
        
        List<Autor> autores = autorRepositorio.findAll();
        modelo.put("autores", autores);
        List<Editorial> editoriales = editorialRepositorio.findAll();
        modelo.put("editoriales", editoriales);
        
         List<Libro> libros = new ArrayList<>();

        if (categoria == null) {
            libros = libroServicio.buscarTodas();
        } else {
            Categoria parsedCategoria = Categoria.valueOf(categoria);
            libros = libroServicio.buscarPorCategoria(parsedCategoria);
        }

        modelo.put("libros", libros);
        
        return "libro.html";
    }

    @GetMapping("/editorial")
    public String editorial() {
        return "editorial.html";
    }

    @GetMapping("/registro")
    public String registro() {
        return "registro.html";
    }

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, @RequestParam(required = false) String logout, ModelMap model) {

        if (error != null) {
            model.put("error", "Mail o contraseña incorrecta");
        }
        if (logout != null) {
            model.put("logout", "Cerraste sesión correctamente.");
        }
        return "/login";
    }

    @PostMapping("/registrar")
    public String registrar(ModelMap modelo, @RequestParam String nombre, @RequestParam String mail, @RequestParam String contrasenia, @RequestParam String contrasenia2) {
        try {
            usuarioServicio.registrar(nombre, mail, contrasenia, contrasenia2);
        } catch (ErrorServicio ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("mail", mail);
            modelo.put("contrasenia", contrasenia);
            modelo.put("contrasenia2", contrasenia2);
            return "registro.html";
        }
        return "index.html";
    }

}
