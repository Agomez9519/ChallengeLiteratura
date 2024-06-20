package com.alura.literatura;

import com.alura.literatura.principal.Principal;
import com.alura.literatura.repository.AutorRepository;
import com.alura.literatura.repository.LibroRepository;
import com.alura.literatura.service.ConsumoAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraturaApplication implements CommandLineRunner {

	//Se instancian los Repositorios de libros y autores
	@Autowired
	private LibroRepository repository;

	@Autowired
	private AutorRepository autorRepository;

	public static void main(String[] args) {
		SpringApplication.run(LiteraturaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

//		var api = new ConsumoAPI();
//		var json = api.obtenerDatos("1don%20quijote");
//		System.out.println(json);

	Principal principal = new Principal(repository,autorRepository);
	principal.muestraElMenu();




	}

}
