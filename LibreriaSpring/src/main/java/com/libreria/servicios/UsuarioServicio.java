package com.libreria.servicios;

import com.libreria.entidades.Usuario;
import com.libreria.errores.ErrorServicio;
import com.libreria.repositorios.UsuarioRepositorio;
import com.libreria.enumeracion.Rol;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class UsuarioServicio implements UserDetailsService {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Transactional(rollbackFor = {Exception.class})
    public void registrar(String nombre, String mail, String contrasenia, String contrasenia2) throws ErrorServicio {

        validar(nombre, mail, contrasenia, contrasenia2);

        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setMail(mail);

        usuario.setContrasenia(new BCryptPasswordEncoder().encode(contrasenia));
        usuario.setRol(Rol.USER);

        usuarioRepositorio.save(usuario);
    }

    @Transactional(rollbackFor = {Exception.class})
    public void modificar(String id, String nombre, String mail, String contrasenia, String contrasenia2) throws ErrorServicio {

        validar(nombre, mail, contrasenia, contrasenia2);

        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Usuario usuario = respuesta.get();
            usuario.setNombre(nombre);
            usuario.setMail(mail);
            usuario.setContrasenia(new BCryptPasswordEncoder().encode(contrasenia));

            usuarioRepositorio.save(usuario);
        } else {
            throw new ErrorServicio("El usuario ingresado no existe");
        }

    }

    private void validar(String nombre, String mail, String contrasenia, String contrasenia2) throws ErrorServicio {

        if (nombre == null || nombre.isEmpty()) {
            throw new ErrorServicio("Debes ingresar un nombre.");
        }
        if (mail == null || mail.isEmpty()) {
            throw new ErrorServicio("Debes ingresar un mail.");
        }
        if (contrasenia == null || contrasenia.isEmpty() || contrasenia.length() < 6) {
            throw new ErrorServicio("Debes ingresar una contrasenia y debe contener al menos 6 caracteres.");
        }
        if (!contrasenia.equals(contrasenia2)) {
            throw new ErrorServicio("Las contraseñas deben ser iguales");
        }

    }

    @Transactional(readOnly = true)
    public Usuario buscarPorId(String id) throws ErrorServicio {

        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
        if (respuesta.isPresent()) {
            return respuesta.get();
        } else {
            throw new ErrorServicio("El usuario que buscas no existe.");
        }

    }

    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepositorio.buscarPorMail(mail);
        if (usuario != null) {
            List<GrantedAuthority> permiso = new ArrayList();

            GrantedAuthority p1 = new SimpleGrantedAuthority("ROL_USER_REGISTRADO");
            permiso.add(p1);

            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

            HttpSession session = attr.getRequest().getSession(true);
            session.setAttribute("usuariosession", usuario);

            return new User(usuario.getMail(), usuario.getContrasenia(), permiso);
        } else {
            return null;
        }
    }

}
