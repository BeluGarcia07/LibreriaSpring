package com.libreria.servicios;

import com.libreria.entidades.Autor;
import com.libreria.entidades.Editorial;
import com.libreria.entidades.Libro;
import com.libreria.entidades.Foto;
import com.libreria.enumeracion.Categoria;
import com.libreria.errores.ErrorServicio;
import com.libreria.repositorios.LibroRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class LibroServicio {

    @Autowired
    private LibroRepositorio libroRepositorio;
    @Autowired
    private AutorServicio autorServicio;
    @Autowired
    private EditorialServicio editorialServicio;
    @Autowired
    private FotoServicio fotoServicio;

    private void validar(Long isbn, String titulo, Integer anio,
            Integer ejemplares, Integer ejemplaresPrestados, Integer ejemplaresRestantes, String idAutor, String idEditorial, Categoria categoria) throws ErrorServicio {

        if (isbn == null) {
            throw new ErrorServicio("El ISBN no puede ser nulo.");
        }
        if (titulo == null || titulo.isEmpty()) {
            throw new ErrorServicio("El título no puede ser nulo.");
        }
        if (anio == null) {
            throw new ErrorServicio("El año no puede ser nulo.");
        }
        if (ejemplares == null) {
            throw new ErrorServicio("Los ejemplares no pueden ser nulos.");
        }
        if (categoria == null) {
            throw new ErrorServicio("La categoria no puede ser nula.");
        }
        if (ejemplaresPrestados == null) {
            throw new ErrorServicio("Los ejemplares prestados no pueden ser nulos.");
        }
        if (ejemplaresRestantes == null) {
            throw new ErrorServicio("Los ejemplares restantes no pueden ser nulos.");
        }
        if (idAutor == null || titulo.isEmpty()) {
            throw new ErrorServicio("El autor no puede ser nulo.");
        }
        if (idEditorial == null || titulo.isEmpty()) {
            throw new ErrorServicio("La editorial no puede ser nulo.");
        }

    }

    @Transactional(rollbackFor = {Exception.class})
    public Libro crear(Long isbn, String titulo, Integer anio, Integer ejemplares, Integer ejemplaresPrestados,
            Integer ejemplaresRestantes, String idAutor, String idEditorial, MultipartFile archivo, Boolean alta, Categoria categoria) throws ErrorServicio {

        validar(isbn, titulo, anio, ejemplares, ejemplaresPrestados, ejemplaresRestantes, idAutor, idEditorial, categoria);
        Libro libro = new Libro();
        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libro.setAnio(anio);
        libro.setEjemplares(ejemplares);
        libro.setEjemplaresPrestados(ejemplaresPrestados);
        libro.setEjemplaresRestantes(ejemplaresRestantes);
        libro.setAlta(true);
        libro.setCategoria(categoria);
        Autor autor = autorServicio.buscarPorId(idAutor);
        libro.setAutor(autor);
        Editorial editorial = editorialServicio.buscarPorId(idEditorial);
        libro.setEditorial(editorial);
        Foto foto = fotoServicio.guardar(archivo);
        libro.setFoto(foto);

        return libroRepositorio.save(libro);
    }

    @Transactional(readOnly = true)
    public Libro buscarPorId(String id) throws ErrorServicio {

        Optional<Libro> respuesta = libroRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Libro libro = respuesta.get();
            return libro;
        } else {
            throw new ErrorServicio("El libro no existe");
        }

    }

    @Transactional(rollbackFor = {Exception.class})
    public Libro modificar(String id, Long isbn, String titulo, Integer anio, Integer ejemplares, Integer ejemplaresPrestados,
            Integer ejemplaresRestantes, String idAutor, String idEditorial, MultipartFile archivo, Boolean alta, 
            Categoria categoria) throws ErrorServicio {

        validar(isbn, titulo, anio, ejemplares, ejemplaresPrestados, ejemplaresRestantes, idAutor, idEditorial, categoria);

        Optional<Libro> respuesta = libroRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Libro libro = respuesta.get();
            libro.setIsbn(isbn);
            libro.setTitulo(titulo);
            libro.setAnio(anio);
            libro.setCategoria(categoria);
            libro.setEjemplares(ejemplares);
            libro.setEjemplaresPrestados(ejemplaresPrestados);
            libro.setEjemplaresRestantes(ejemplaresRestantes);
            libro.setAlta(true);
            Autor autor = autorServicio.buscarPorId(idAutor);
            libro.setAutor(autor);
            Editorial editorial = editorialServicio.buscarPorId(idEditorial);
            libro.setEditorial(editorial);

            String idFoto = null;
            if (libro.getFoto() != null) {
                idFoto = libro.getFoto().getId();
            }
            Foto foto = fotoServicio.actualizar(idFoto, archivo);
            libro.setFoto(foto);

            return libroRepositorio.save(libro);
        } else {
            throw new ErrorServicio("El libro que querés modificar no existe.");
        }

    }

    @Transactional(rollbackFor = {Exception.class})
    public Libro baja(String id) throws ErrorServicio {

        Optional<Libro> respuesta = libroRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Libro libro = respuesta.get();
            libro.setAlta(false);
            return libroRepositorio.save(libro);
        } else {
            throw new ErrorServicio("El libro que querés modificar no existe.");
        }

    }

    @Transactional(readOnly = true)
    public Libro buscarPorID(String id) throws ErrorServicio {
        Optional<Libro> respuesta = libroRepositorio.findById(id);
        if (respuesta.isPresent()) {
            return respuesta.get();
        } else {
            throw new ErrorServicio("El libro solicitado no existe");
        }
    }

//    public List<Libro> buscarPorCategoria(Categoria categoria) throws ErrorServicio {
//
//        return libroRepositorio.buscarPorCategoria(categoria);
//
//    }

    public List<Libro> buscarTodas() throws ErrorServicio {

        return libroRepositorio.findAll();

    }

}
