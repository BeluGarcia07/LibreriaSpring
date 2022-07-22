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
@RequestMapping("/")
public class LibroController {

    @Autowired
    private LibroServicio libroServicio;

    
    @PostMapping("/crearLibro")
    public String crear(ModelMap modelo, @RequestParam Long isbn, @RequestParam String titulo, @RequestParam Integer anio,
            @RequestParam Integer ejemplares, @RequestParam (required = false) String idAutor, @RequestParam (required = false) String idEditorial,
            @RequestParam MultipartFile archivo, @RequestParam Boolean alta, @RequestParam (required = false) Categoria categoria, @RequestParam(required = false) String idUsuario) {

        try {
            libroServicio.crear(isbn, titulo, anio, ejemplares,  idAutor,
                    idEditorial, archivo, true, categoria);
        } catch (ErrorServicio e) {
            modelo.put("errorReg", e.getMessage());
            modelo.put("isbn", isbn);
            modelo.put("titulo", titulo);
            modelo.put("anio", anio);
            modelo.put("ejemplares", ejemplares);
            modelo.put("idAutor", idAutor);
            modelo.put("idEditorial", idEditorial);
            modelo.put("archivo", archivo);
//            modelo.put("categorias", categoria.values());
            return "registro.html";
        }

        modelo.put("titulo", "El libro '" + titulo + "' fue cargada con exito!");
        return "libro.html";

    }
    
    //    EN DESARROLLO
    @GetMapping("/editarLibro/{id}")
    public String modificar(@PathVariable String id, ModelMap modelo) {

        try {
            Libro libro = libroServicio.buscarPorID(id);
            modelo.put("libro", libro);
        } catch (Exception e) {
        }

        return "index.html";
    }
    
    //    EN DESARROLLO
    @PostMapping("/editarLibro")
    public String modificar(ModelMap modelo, @RequestParam String id, @RequestParam Long isbn, @RequestParam String titulo,
            @RequestParam Integer anio,
            @RequestParam Integer ejemplares,
            @RequestParam String idAutor, @RequestParam String idEditorial,
            @RequestParam MultipartFile archivo, @RequestParam Boolean alta, @RequestParam Categoria categoria,
            @RequestParam(required = false) String idUsuario) {

        try {

            libroServicio.modificar(id, isbn, titulo, anio, ejemplares, idAutor,
                    idEditorial, archivo, true, categoria);
        } catch (ErrorServicio e) {
            modelo.put("errorReg", e.getMessage());
            modelo.put("titulo", titulo);
            modelo.put("anio", anio);
            modelo.put("ejemplares", ejemplares);
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
