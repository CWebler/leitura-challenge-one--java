package br.com.alura.leitura.principal;

import br.com.alura.leitura.model.Autor;
import br.com.alura.leitura.model.Livro;
import br.com.alura.leitura.service.LivroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class Principal {

    private Scanner leitura = new Scanner(System.in);
    private final LivroService livroService;

    @Autowired
    public Principal(LivroService livroService) {
        this.livroService = livroService;
    }

    public void menuDeOpcoes() {
        int opcao = -1;
        while (opcao != 0) {
            var menu = """
                    Escolha o número com a sua opção:

                    1 - Buscar livro pelo título
                    2 - Listar livros registrados
                    3 - Listar autores registrados
                    4 - Autores vivos em um determinado ano
                    5 - Listar livros lançados em um determinado ano

                    0 - SAIR
                    """;
            System.out.println(menu);
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1:
                    buscarLivroPeloTitulo();
                    break;
                case 2:
                    listarLivrosRegistrados();
                    break;
                case 3:
                    listarAutoresRegistrados();
                    break;
                case 4:
                    autoresVivosEmUmDeterminadoAno();
                    break;
                case 5:
                    livrosLancadosPorAno();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        }
    }

    private void buscarLivroPeloTitulo() {
        System.out.print("Digite o título do livro: ");
        String titulo = leitura.nextLine();
        Livro livro = livroService.buscarLivroPorTitulo(titulo);
        if (livro != null) {
            System.out.println(livro);
        } else {
            System.out.println("Livro não encontrado.");
        }
    }

    private void listarLivrosRegistrados() {
        List<Livro> livros = livroService.listarTodosLivros();
        if (livros.isEmpty()) {
            System.out.println("Nenhum livro registrado ainda.");
        } else {
            livros.forEach(System.out::println);
        }
    }

    private void listarAutoresRegistrados() {
        List<Autor> autores = livroService.listarTodosAutores();
        if (autores.isEmpty()) {
            System.out.println("Nenhum autor registrado ainda.");
        } else {
            autores.forEach(System.out::println);
        }
    }

    private void autoresVivosEmUmDeterminadoAno() {
        System.out.print("Digite o ano: ");
        int ano = leitura.nextInt();
        leitura.nextLine();
        List<Autor> autores = livroService.listarAutoresVivosNoAno(ano);
        if (autores.isEmpty()) {
            System.out.println("Nenhum autor vivo nesse ano.");
        } else {
            autores.forEach(System.out::println);
        }
    }

    private void livrosLancadosPorAno() {
        System.out.println("Função não implementada ainda.");
    }
}
