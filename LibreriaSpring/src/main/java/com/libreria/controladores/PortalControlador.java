package com.libreria.controladores;

import com.libreria.errores.ErrorServicio;
import com.libreria.servicios.UsuarioServicio;
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
    
    @GetMapping("/")
    public String index() {
        return "index.html";
    }

    @GetMapping("/autor")
    public String autor() {
        return "autor.html";
    }

    @GetMapping("/libro")
    public String libro() {
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
         
        if (error!=null) {
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
