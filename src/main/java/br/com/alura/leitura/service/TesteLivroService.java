package br.com.alura.leitura.service;

import br.com.alura.leitura.model.Livro;
import br.com.alura.leitura.service.LivroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;

@SpringBootApplication
public class TesteLivroService implements CommandLineRunner {

    @Autowired
    private LivroService livroService;

    public static void main(String[] args) {
        SpringApplication.run(TesteLivroService.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Livro livro = livroService.buscarLivroPorTitulo("Pride and Prejudice");
        if (livro != null) {
            System.out.println(livro);
        } else {
            System.out.println("Livro não encontrado");
        }
    }
}
