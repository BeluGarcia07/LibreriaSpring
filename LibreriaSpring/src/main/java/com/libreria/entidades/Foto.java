
package com.libreria.entidades;
    
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class Foto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)      
    private String id;
    private String nombre;
    private String mime;
    
    @Lob @Basic(fetch = FetchType.LAZY)
    private byte[] contenido;

    public Foto() {
    }

    public Foto(String id, String nombre, String mime, byte[] contenido) {
        this.id = id;
        this.nombre = nombre;
        this.mime = mime;
        this.contenido = contenido;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getMime() {
        return mime;
    }

    public byte[] getContenido() {
        return contenido;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setMime(String mime) {
        this.mime = mime;
    }

    public void setContenido(byte[] contenido) {
        this.contenido = contenido;
    }
    
    
}
