package br.com.alura.litter.alura;

import br.com.alura.litter.alura.main.Main;
import br.com.alura.litter.alura.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AluraApplication implements CommandLineRunner {

	@Autowired
	private LivroRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(AluraApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		Main main = new Main(repository);
		main.menu();

	}
}
