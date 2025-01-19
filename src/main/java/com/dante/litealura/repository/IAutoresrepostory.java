package com.dante.litealura.repository;

import com.dante.litealura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IAutoresrepostory extends JpaRepository<Autor,Long> {
    Autor findByNombreIgnoreCase(String nombre);
    List<Autor> findByFechaDeNacimientoLessThanEqualAndFechaDeFallecimientoGreaterThanEqual(int anioInicial, int anioFinal);

}
