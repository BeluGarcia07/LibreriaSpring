package com.libreria.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class PortalControlador {

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
}
