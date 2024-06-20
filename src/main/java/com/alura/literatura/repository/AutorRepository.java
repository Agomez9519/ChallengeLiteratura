package com.alura.literatura.repository;

import com.alura.literatura.model.Autor;
import com.alura.literatura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AutorRepository extends JpaRepository<Autor, Long> {

    Autor findByNombre(String nombre);


//    @Query("SELECT a FROM Autor WHERE a.fechaDeFallecimiento <= :fecha")
    List<Autor> findByFechaDeFallecimientoGreaterThanEqual(int fecha);
}
