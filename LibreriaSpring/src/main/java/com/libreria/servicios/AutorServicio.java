package com.libreria.servicios;

import com.libreria.entidades.Autor;
import com.libreria.entidades.Foto;
import com.libreria.errores.ErrorServicio;
import com.libreria.repositorios.AutorRepositorio;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class AutorServicio {

    @Autowired
    private AutorRepositorio autorRepositorio;
    @Autowired
    private FotoServicio fotoServicio;

    @Transactional(rollbackFor = {Exception.class})
    public Autor crear(String nombre, MultipartFile archivo) throws ErrorServicio{
        Autor autor = new Autor();
        autor.setNombre(nombre);
        autor.setAlta(true);
        
        Foto foto = fotoServicio.guardar(archivo);
        autor.setFoto(foto);

        return autorRepositorio.save(autor);
    }

    @Transactional(readOnly = true)
    public Autor buscarPorId(String id) throws ErrorServicio {
        Optional<Autor> respuesta = autorRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Autor autor = respuesta.get();
            return autor;
        } else {
            throw new ErrorServicio("El autor no existe");
        }
    }

    @Transactional(rollbackFor = {Exception.class})
    public Autor baja(String id) throws ErrorServicio {

        Optional<Autor> respuesta = autorRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Autor autor = respuesta.get();
            autor.setAlta(false);
            return autorRepositorio.save(autor);
        } else {
            throw new ErrorServicio("El autor no existe.");
        }
    }

    @Transactional(rollbackFor = {Exception.class})
    public Autor editar(String id, String nombre, MultipartFile archivo) throws ErrorServicio {

        Optional<Autor> respuesta = autorRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Autor autor = respuesta.get();
            autor.setNombre(nombre);
            
            String idFoto = null;
            if (autor.getFoto() != null) {
                idFoto = autor.getFoto().getId();
            }
            
            Foto foto = fotoServicio.actualizar(idFoto, archivo);
            autor.setFoto(foto);
            
            return autorRepositorio.save(autor);
        } else {
            throw new ErrorServicio("El autor no existe.");
        }
    }

}
