

import br.com.alura.leitura.model.Livro;
import br.com.alura.leitura.service.LivroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TesteBuscaLivro implements CommandLineRunner {

    @Autowired
    private LivroService livroService;

    public static void main(String[] args) {
        SpringApplication.run(TesteBuscaLivro.class, args);
    }

    @Override
    public void run(String... args) {
        Livro livro = livroService.buscarLivroPorTitulo("Pride and Prejudice");
        System.out.println(livro != null ? livro : "Livro não encontrado.");
    }
}
