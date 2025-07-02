package br.com.alura.leitura.service;

import br.com.alura.leitura.model.Autor;
import br.com.alura.leitura.model.Livro;
import br.com.alura.leitura.repository.AutorRepository;
import br.com.alura.leitura.repository.LivroRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LivroService {
    private static final String BASE_URL = "https://gutendex.com/books/?search=";

    private final ObjectMapper mapper;
    private final LivroRepository livroRepository;
    private final AutorRepository autorRepository;

    public LivroService(LivroRepository livroRepository, AutorRepository autorRepository) {
        this.mapper = new ObjectMapper();
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
    }

    public Livro buscarLivroPorTitulo(String titulo) {
        try {
            String url = BASE_URL + titulo.replace(" ", "%20");
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            JsonNode root = mapper.readTree(response.body());
            JsonNode primeiroLivroJson = root.path("results").get(0);

            Livro livro = mapper.treeToValue(primeiroLivroJson, Livro.class);

            if (livro.getAutores() != null && !livro.getAutores().isEmpty()) {
                Autor autorJson = livro.getAutores().get(0);

                Optional<Autor> autorExistente = autorRepository.findByNomeIgnoreCase(autorJson.getNome());

                Autor autorFinal = autorExistente.orElseGet(() -> autorRepository.save(autorJson));

                livro.setAutores(List.of(autorFinal));
            }

            return livroRepository.save(livro);
        } catch (Exception e) {
            System.out.println("Erro ao buscar livro: " + e.getMessage());
            return null;
        }
    }

    public List<Livro> listarTodosLivros() {
        return livroRepository.findAll();
    }

    public List<Livro> listarLivrosPorIdioma(String idioma) {
        return livroRepository.findAll().stream()
                .filter(l -> l.getPrimeiroIdioma() != null && l.getPrimeiroIdioma().equalsIgnoreCase(idioma))
                .collect(Collectors.toList());
    }

    public List<Autor> listarTodosAutores() {
        return autorRepository.findAll();
    }

    public List<Autor> listarAutoresVivosNoAno(int ano) {
        return autorRepository.findAll().stream()
                .filter(a -> a.getAnoNascimento() != null && a.getAnoNascimento() <= ano)
                .filter(a -> a.getAnoFalecimento() == null || a.getAnoFalecimento() > ano)
                .collect(Collectors.toList());
    }
}
