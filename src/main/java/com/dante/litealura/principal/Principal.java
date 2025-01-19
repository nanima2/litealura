package com.dante.litealura.principal;

import com.dante.litealura.model.*;
import com.dante.litealura.repository.IAutoresrepostory;
import com.dante.litealura.repository.ILibrosrepository;
import com.dante.litealura.service.ConsumoApi;
import com.dante.litealura.service.ConvierteDatos;

import javax.lang.model.element.NestingKind;
import java.util.*;
public class Principal {
    private ConsumoApi consumoApi=new ConsumoApi();
    private ConvierteDatos conversor = new ConvierteDatos();
    private Scanner teclado=new Scanner(System.in);
    private final IAutoresrepostory autorRepos;
    private final ILibrosrepository libroRepos;
    private final String URL_ = "https://gutendex.com/books/?search=";
    String mInicio = """
                \n 
                *****************************************
                Elija la opción :
                 1- Buscar el libro por titulo.
                 2- Buscar los libros registrados en la base de datos.
                 3- Listar los autores registrados
                 4- Listar los autores vivos en un determinado año
                 5- Listar los libros por idioma
                 0- Salir   
                 ***************************************
                """;

    public Principal(IAutoresrepostory autorRepos, ILibrosrepository libroRepos) {
        this.autorRepos = autorRepos;
        this.libroRepos = libroRepos;
    }
    public void saleMenu()
    {
        var ocpion=100;
        while (ocpion!=0)
        {
            System.out.println(mInicio);
            ocpion= teclado.nextInt();
            switch (ocpion){
                case 1->buscaTitulo();
                case 2->listaLibrosRegistrados();
                case 3->listaAutoresRegistrados();
                case 4->autoresAno();
                case 5->idiomas();
            }
        }
    }

    private void idiomas() {
        System.out.println("""
                                escribe el idioma a buscar
                                es - Español
                                en - Inglés
                                fr - Francés
                                pt - Portugués
                
                """);
        String idI=teclado.nextLine();
        List<Libro> librosIdioma = libroRepos.findByidiomaContaining(idI);
        librosIdioma.stream()
                .sorted(Comparator.comparing(Libro::getTitulo))
                .forEach(System.out::println);
    }

    private void autoresAno() {
        System.out.println("año que deseas buscar");
        int ano=teclado.nextInt();
        List<Autor>autoresaano=autorRepos.findByFechaDeNacimientoLessThanEqualAndFechaDeFallecimientoGreaterThanEqual(ano,ano);
        autoresaano.stream()
                .sorted(Comparator.comparing(Autor::getNombre))
                .forEach(System.out::println);
    }

    private void listaAutoresRegistrados() {
        List<Autor> autores = autorRepos.findAll();
        autores.stream()
                .sorted(Comparator.comparing(Autor::getNombre))
                .forEach(System.out::println);
    }


    private void buscaTitulo() {
        System.out.println("digite el nombre del libro");
        Datos datos=getDatosL();
        DatosLibro datosLibros = datos.resultado().get(0);
        DatosAutor datosAutores = datosLibros.autor().get(0);
        Libro libroAparecio= libroRepos.findBytitulo(datosLibros.titulo());
        if (libroAparecio != null) {
            System.out.println("Este libro ya est registrado");
        } else {
            Autor autorAparecio = autorRepos.findByNombreIgnoreCase(datosAutores.nombreAutor());
            if (autorAparecio != null) {
                Libro nuevoLibro = new Libro(datosLibros, autorAparecio);
                libroRepos.save(nuevoLibro);
                System.out.println("-libro creado\n" + nuevoLibro);
            } else {
                Autor nuevoAutor = new Autor(datosAutores);
                nuevoAutor = autorRepos.save(nuevoAutor);
                Libro nuevoLibro = new Libro(datosLibros, nuevoAutor);
                libroRepos.save(nuevoLibro);
                System.out.println("- Libro\n" + nuevoLibro + "\n");

            }

    }

    }
    private Datos getDatosL()
    {
        String tituloL=teclado.nextLine();
        var json= consumoApi.obtenerDatosAPI(URL_ + tituloL.replace(" ", "+"));
        Datos datosL=conversor.obtenerDatos(json,Datos.class);
        return datosL;
    }
    private void listaLibrosRegistrados() {
        List<Libro> libros = libroRepos.findAll();

        System.out.println("- La Lista De Los Libros: \n");
        libros.stream()
                .sorted(Comparator.comparing(Libro::getTitulo))
                .forEach(libro -> {
                    System.out.println("-LIBRO");
                    System.out.println(libro);

                });
    }

}

