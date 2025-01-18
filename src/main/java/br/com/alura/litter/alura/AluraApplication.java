package br.com.alura.litter.alura;

import br.com.alura.litter.alura.main.Main;
import br.com.alura.litter.alura.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.security.Principal;

@SpringBootApplication
public class AluraApplication implements CommandLineRunner {

	@Autowired
	private BookRepository repository;

	@Autowired
	private Main main;

	public static void main(String[] args) {
		SpringApplication.run(AluraApplication.class, args);
	}


//	@Override
//	public void run(String... args) throws Exception {
//		Main main = new Main(repository);
//		main.menu();
//
//	}

	@Override
	public void run(String... args) throws Exception {
		main.menu();

	}
}
