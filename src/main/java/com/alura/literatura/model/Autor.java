package com.alura.literatura.model;

import com.alura.literatura.service.LibroService;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name="autores",uniqueConstraints=@UniqueConstraint(columnNames={"nombre"}))
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String nombre;


    private int fechaDeNacimiento;

    private int fechaDeFallecimiento;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Column(unique = true)
    private List<Libro> libros;

    public Autor(){}

    public Autor(DatosAutor datosAutor) {
        this.nombre = datosAutor.nombre();
        this.fechaDeNacimiento = datosAutor.fechaDeNacimiento();
        this.fechaDeFallecimiento = datosAutor.fechaDeFallecimiento();
    }

    public List<Libro> getLibros(){return libros;};

    public void setLibros(List<Libro> libros){
        libros.forEach(l -> l.setAutor(this));
        this.libros = libros;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getFechaDeNacimiento() {
        return fechaDeNacimiento;
    }

    public void setFechaDeNacimiento(int fechaDeNacimiento) {
        this.fechaDeNacimiento = fechaDeNacimiento;
    }

    public int getFechaDeFallecimiento() {
        return fechaDeFallecimiento;
    }

    public void setFechaDeFallecimiento(int fechaDeFallecimiento) {
        this.fechaDeFallecimiento = fechaDeFallecimiento;
    }

    public void imprimirLibros(){




//        for (int i = 0; i < libros.size(); i++) {
//            System.out.println("\nAutor: " + nombre + "\n" +
//                    "Fecha de nacimiento: " + fechaDeNacimiento + "\n" +
//                    "Fecha de fallecimiento: " + fechaDeFallecimiento + "\n" +
//                    "Libros: [ " + libros.get(i).getTitulo() +  "]" + "\n" +
//                    "----------------\n");
//        }
    }

    @Override
    public String toString() {

        List<String> librosPorAutor = libros.stream().map(l -> l.getTitulo()).collect(Collectors.toList());

        //System.out.println(librosPorAutor);

        return  "\nAutor: " + nombre + "\n" +
                "Fecha de nacimiento: " + fechaDeNacimiento + "\n" +
                "Fecha de fallecimiento: " + fechaDeFallecimiento + "\n" +
                "Libros: " + librosPorAutor  + "\n" +
                "----------------\n";
    }
}
