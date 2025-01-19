package com.dante.litealura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibro(
    @JsonAlias("title")
    String titulo,
    @JsonAlias("authors")
    List<DatosAutor> autor,
    @JsonAlias("languages")
    List<String> idiomas,
    @JsonAlias("download_count")
    Integer numeroDeDescargas
){
    @Override
    public String toString() {
        return  " titulo: " + titulo + '\n' +
                " autor: " + autor +'\n' +
                " idioma: " + idiomas +'\n' +
                " numero Descargas: " + numeroDeDescargas;
    }
}

