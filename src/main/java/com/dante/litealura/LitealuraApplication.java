package com.dante.litealura;
import com.dante.litealura.principal.Principal;
import com.dante.litealura.repository.IAutoresrepostory;
import com.dante.litealura.repository.ILibrosrepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
public class LitealuraApplication implements CommandLineRunner {
	@Autowired
	private IAutoresrepostory autorRepos;
	@Autowired
	private ILibrosrepository libroRepos;

	public static void main(String[] args) {
		SpringApplication.run(LitealuraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(autorRepos,libroRepos);
		principal.saleMenu();

	}
}
