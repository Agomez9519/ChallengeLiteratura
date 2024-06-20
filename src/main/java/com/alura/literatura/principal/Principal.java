package com.alura.literatura.principal;

import com.alura.literatura.model.*;
import com.alura.literatura.repository.AutorRepository;
import com.alura.literatura.repository.LibroRepository;
import com.alura.literatura.service.ConsumoAPI;
import com.alura.literatura.service.ConvierteDatos;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private final String URL_BASE = "http://gutendex.com/books/?search=";
    private ConvierteDatos conversor = new ConvierteDatos();
    private LibroRepository repositorio;
    private AutorRepository autorRepository;

    public Principal(LibroRepository repository, AutorRepository autorRepository) {
        this.repositorio = repository;
        this.autorRepository = autorRepository;
    }


    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    Elija la opcion a traves de su número:
                    1 - Buscar libro por titulo
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos en un determinado año
                    5 - Listar libros por idioma                  
                    0 - Salir
                    """;

            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextInt();

            switch (opcion) {
                case 1:
                    guardarLibro();
                    break;
                case 2:
                    buscarLibrosRegistrados();
                    break;
                case 3:
                    listarAutores();
                    break;
                case 4:
                    listarAutoresPorAnio();
                    break;
                case 5:
                    listarPorIdioma();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
            }
        }

    }


    private void listarPorIdioma() {
        var menuIdioma = """
                Ingrese el idioma para buscar los libros:
                es - Español
                en - Ingles
                fr - Frances
                pt - Portugues
                """;
        System.out.println(menuIdioma);
        var idioma = teclado.next();

        List<Libro> librosIdioma = repositorio.findByIdioma(idioma);

        librosIdioma.stream()
                .forEach(s -> System.out.println(s.toString()));

    }

    private void listarAutoresPorAnio() {
        System.out.println("Ingrese el año vivi del autor(es) que desea buscar");
        var anio = teclado.nextInt();

        List<Autor> autoresFecha = autorRepository.findByFechaDeFallecimientoGreaterThanEqual(anio);

        autoresFecha.forEach(s -> System.out.println(s.toString()));
    }

    private void listarAutores() {
        List<Autor> autores = new ArrayList<>();
        autores = autorRepository.findAll();

        autores.forEach(s -> System.out.println(s.toString()));

    }

    private void buscarLibrosRegistrados() {
        List<Libro> libros = new ArrayList<>();
        libros = repositorio.findAll();

        libros.forEach(s -> System.out.println(s.toString()));
    }

    private DatosLibro getLibro() {
        System.out.println("Escribe el nombre del libro que deseas buscar");
        var nombreLibro = teclado.nextLine();
        var json = consumoApi.obtenerDatos(URL_BASE + nombreLibro.replace(" ", "%20").toLowerCase());
        //se extrae la respuesta al record DatosResponse
        DatosResponse libros = conversor.obtenerDatos(json, DatosResponse.class);

        //se extrae el libro n1 del arrayList response del DatosResponse
        DatosLibro libro = libros.response().get(0);
        return libro;

    }

    public void guardarLibro() {

        //se extra el libro y su autor desde el metodo getLibro()
        DatosLibro libro = getLibro();
        DatosAutor autor = libro.autor().get(0);

        //Se guarda el autor primero en la BD
        Autor autorResponse = new Autor(autor);

        //Se almacena en una variabe el libro guardado
        Libro libroGuardado = new Libro(libro);

        Optional<Autor> autorEncontrado = Optional.ofNullable(autorRepository.findByNombre(autorResponse.getNombre()));

        //!autorEncontrado.getNombre().equalsIgnoreCase(autorResponse.getNombre())
        //Si el autor no esta presente se guarda el autor y el libro
        if (!autorEncontrado.isPresent()) {
            autorRepository.save(autorResponse);

            //se setea el autor correspondiente del libro a guardar
            libroGuardado.setAutor(autorResponse);

            //Se guarda en la BD
            repositorio.save(libroGuardado);

        } else {
            //se setea el autor correspondiente del libro a guardar
            libroGuardado.setAutor(autorEncontrado.get());

            //Se guarda en la BD
            repositorio.save(libroGuardado);
        }


        System.out.println("----- Libro -----\n" +
                "Titulo: " + libroGuardado.getTitulo() + "\n" +
                "Autor: " + libroGuardado.getAutor().getNombre() + "\n" +
                "Idioma: " + libroGuardado.getIdioma() + "\n" +
                "Numero de descargas: " + libroGuardado.getNumeroDeDescargas() + "\n" +
                "-----------------");
    }
}


