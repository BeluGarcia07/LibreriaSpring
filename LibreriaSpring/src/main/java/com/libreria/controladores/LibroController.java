package com.libreria.controladores;

import com.libreria.entidades.Libro;
import com.libreria.enumeracion.Categoria;
import com.libreria.errores.ErrorServicio;
import com.libreria.servicios.LibroServicio;
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
@RequestMapping("/libro")
public class LibroController {

    @Autowired
    private LibroServicio libroServicio;

    @PostMapping("/crear")
    public String crear(ModelMap modelo, @RequestParam Long isbn, @RequestParam String titulo, @RequestParam Integer anio,
            @RequestParam Integer ejemplares, @RequestParam Integer ejemplaresPrestados, @RequestParam Integer ejemplaresRestantes, @RequestParam String idAutor, @RequestParam String idEditorial,
            @RequestParam MultipartFile archivo, @RequestParam Boolean alta, @RequestParam Categoria categoria, @RequestParam(required = false) String idUsuario) {

        try {
            libroServicio.crear(isbn, titulo, anio, ejemplares, ejemplaresPrestados, ejemplaresRestantes, idAutor,
                    idEditorial, archivo, true, categoria);
        } catch (ErrorServicio e) {
            modelo.put("error", e.getMessage());
            modelo.put("titulo", titulo);
            modelo.put("anio", anio);
            modelo.put("ejemplares", ejemplares);
            modelo.put("ejemplaresPrestados", ejemplaresPrestados);
            modelo.put("ejemplaresRestantes", ejemplaresRestantes);
            modelo.put("idAutor", idAutor);
            modelo.put("idEditorial", idEditorial);
            modelo.put("archivo", archivo);
            modelo.put("categoria", categoria);
            modelo.put("archivo", archivo);
            return "registro.html";
        }

        modelo.put("titulo", "El libro '" + titulo + "' fue cargada con exito!");
        return "redirect:/index";

    }

    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap modelo) {

        try {
            Libro libro = libroServicio.buscarPorID(id);
            modelo.put("libro", libro);
        } catch (Exception e) {
        }

        return "/modificarLibro.html";
    }

    @PostMapping
    public String modificar(ModelMap modelo, @RequestParam String id, @RequestParam Long isbn, @RequestParam String titulo,
            @RequestParam Integer anio,
            @RequestParam Integer ejemplares, @RequestParam Integer ejemplaresPrestados, @RequestParam Integer ejemplaresRestantes,
            @RequestParam String idAutor, @RequestParam String idEditorial,
            @RequestParam MultipartFile archivo, @RequestParam Boolean alta, @RequestParam Categoria categoria,
            @RequestParam(required = false) String idUsuario) {

        try {

            libroServicio.modificar(id, isbn, titulo, anio, ejemplares, ejemplaresPrestados, ejemplaresRestantes, idAutor,
                    idEditorial, archivo, true, categoria);
        } catch (ErrorServicio e) {
            modelo.put("error", e.getMessage());
            modelo.put("titulo", titulo);
            modelo.put("anio", anio);
            modelo.put("ejemplares", ejemplares);
            modelo.put("ejemplaresPrestados", ejemplaresPrestados);
            modelo.put("ejemplaresRestantes", ejemplaresRestantes);
            modelo.put("idAutor", idAutor);
            modelo.put("idEditorial", idEditorial);
            modelo.put("archivo", archivo);
            modelo.put("categoria", categoria);
            modelo.put("archivo", archivo);
            return "registro.html";    
        }
        modelo.put("exito", "Se edito el libro '" + titulo + "' con exito!");
        return "/index.html";
    }

}
