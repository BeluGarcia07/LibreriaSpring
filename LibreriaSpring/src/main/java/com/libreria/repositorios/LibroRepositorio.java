package com.libreria.repositorios;

import com.libreria.entidades.Libro;
import com.libreria.enumeracion.Categoria;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LibroRepositorio extends JpaRepository<Libro, String> {
//
//    @Query("SELECT l FROM Libro l WHERE l.autor LIKE :autor")
//    public Libro buscarPorId(@Param("autor") String autor);  

      @Query("SELECT l FROM Libro l WHERE l.categoria LIKE :categoria")
        public List<Libro> buscarPorCategoria(@Param("categoria") String categoria);

        @Query("SELECT l FROM Libro l WHERE l.categoria LIKE :categoria")
        public List<Libro> buscarPorCategoria(@Param("categoria") Categoria categoria);
      
    
}
