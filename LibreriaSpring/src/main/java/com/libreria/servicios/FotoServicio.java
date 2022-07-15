package com.libreria.servicios;

import com.libreria.entidades.Foto;
import com.libreria.errores.ErrorServicio;
import com.libreria.repositorios.FotoRepositorio;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FotoServicio {

    @Autowired
    private FotoRepositorio fotoRepositorio;

    @Transactional(rollbackOn = {Exception.class})
    public Foto guardar(MultipartFile archivo) throws ErrorServicio {
        if (archivo != null) {
            try {
                Foto foto = new Foto();
                foto.setMime(archivo.getContentType());
                foto.setNombre(archivo.getName());
                foto.setContenido(archivo.getBytes());

                return fotoRepositorio.save(foto);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return null;
    }

//    *********************************************************
//    PREGUNTAR A VALEN SI ESTA BIEN HECHO ESTE METODO!!!
//    EN EL VIDEO JUANMA LO HACE DE OTRA FORMA!!!
//    *********************************************************
    @Transactional(rollbackOn = {Exception.class})
    public Foto actualizar(String idFoto, MultipartFile archivo) throws ErrorServicio {
        if (archivo != null) {

            Optional<Foto> respuesta = fotoRepositorio.findById(idFoto);
            if (respuesta.isPresent()) {
                try {
                    Foto foto = respuesta.get();
                    foto.setMime(archivo.getContentType());
                    foto.setNombre(archivo.getName());
                    foto.setContenido(archivo.getBytes());
                    return fotoRepositorio.save(foto);
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }

        }
        return null;
    }
}
