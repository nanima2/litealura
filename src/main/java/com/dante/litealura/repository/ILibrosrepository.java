package com.dante.litealura.repository;

import com.dante.litealura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ILibrosrepository extends JpaRepository<Libro,Long> {
    Libro findBytitulo(String titulo);
    List<Libro> findByidiomaContaining(String idiomas);
    @Query("SELECT l FROM Libro l LEFT JOIN FETCH l.idioma")
    List<Libro> findAllWithIdiomas();
}


