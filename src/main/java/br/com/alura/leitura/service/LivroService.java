package br.com.alura.leitura.service;

import br.com.alura.leitura.dto.EstatisticaIdiomaDTO;
import br.com.alura.leitura.model.Autor;
import br.com.alura.leitura.model.Livro;
import br.com.alura.leitura.repository.AutorRepository;
import br.com.alura.leitura.repository.LivroRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class LivroService {

    private static final String BASE_URL = "https://gutendex.com/books/?search=";

    private final ObjectMapper mapper;
    private final LivroRepository livroRepository;
    private final AutorRepository autorRepository;
    private final HttpClient client = HttpClient.newHttpClient();

    public LivroService(LivroRepository livroRepository, AutorRepository autorRepository, ObjectMapper mapper) {
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
        this.mapper = mapper;
    }

    @Transactional
    public Livro buscarLivroPorTitulo(String titulo) {
        Optional<Livro> salvo = livroRepository.findFirstByTituloIgnoreCase(titulo);
        if (salvo.isPresent()) {
            return salvo.get();
        }

        try {
            String url = BASE_URL + titulo.replace(" ", "%20");
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            JsonNode root = mapper.readTree(response.body());
            JsonNode resultados = root.path("results");
            if (resultados.isEmpty() || resultados.get(0) == null) {
                return null;
            }

            JsonNode primeiroLivroJson = resultados.get(0);
            Livro livro = mapper.treeToValue(primeiroLivroJson, Livro.class);

            if (livro.getAnoLancamento() == null && primeiroLivroJson.hasNonNull("copyright_year")) {
                livro.setAnoLancamento(primeiroLivroJson.get("copyright_year").asInt());
            }

            if (livro.getAutores() != null && !livro.getAutores().isEmpty()) {
                Autor autorJson = livro.getAutores().iterator().next();
                Optional<Autor> autorExistente = autorRepository.findByNomeIgnoreCase(autorJson.getNome());
                Autor autorFinal = autorExistente.orElseGet(() -> autorRepository.save(autorJson));
                livro.setAutores(Set.of(autorFinal));
            }

            return livroRepository.save(livro);

        } catch (Exception e) {
            System.out.println("Erro ao buscar livro: " + e.getMessage());
            return null;
        }
    }

    @Transactional(readOnly = true)
    public List<Livro> listarTodosLivros() {
        return livroRepository.findAllComAutoresEIdiomas();
    }


    @Transactional(readOnly = true)
    public List<Livro> listarLivrosPorIdioma(String idioma) {
        return livroRepository.findByIdiomaComAutoresEIdiomas(idioma);
    }

    @Transactional(readOnly = true)
    public EstatisticaIdiomaDTO contarPorIdioma(String idioma) {
        long quantidade = livroRepository.contarPorIdioma(idioma);
        return new EstatisticaIdiomaDTO(idioma, quantidade);
    }

    @Transactional(readOnly = true)
    public List<Autor> listarTodosAutores() {
        return autorRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Autor> listarAutoresVivosNoAno(int ano) {
        return autorRepository.findAutoresVivosEm(ano);

    }

    @Transactional(readOnly = true)
    public List<Livro> listarLivrosPorAno(int ano) {
        return livroRepository.findByAnoLancamento(ano);
    }

}
