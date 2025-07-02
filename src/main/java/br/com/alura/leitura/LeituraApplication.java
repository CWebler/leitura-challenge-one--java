package br.com.alura.leitura;

import br.com.alura.leitura.principal.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;

@SpringBootApplication
public class LeituraApplication implements CommandLineRunner {

	private final Principal principal;

	@Autowired
	public LeituraApplication(Principal principal) {
		this.principal = principal;
	}

	public static void main(String[] args) {
		SpringApplication.run(LeituraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		principal.menuDeOpcoes();
	}
}
