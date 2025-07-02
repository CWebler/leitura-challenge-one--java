package br.com.alura.leitura.service;

import br.com.alura.leitura.helper.JsonMapper;
import br.com.alura.leitura.model.RespostaApi;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

@Service
public class SolicitacaoAPI {

    private static final HttpClient CLIENT = HttpClient.newHttpClient();

    public String obterDados(String endereco) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endereco))
                .timeout(Duration.ofSeconds(10))
                .GET()
                .build();

        HttpResponse<String> response;
        try {
            response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Falha na comunicação com a API: " + endereco, e);
        }

        if (response.statusCode() < 200 || response.statusCode() >= 300) {
            throw new RuntimeException("Erro da API (" + response.statusCode() + "): " + response.body());
        }

        return response.body();
    }

    public RespostaApi obterLivrosComoObjeto(String endereco) {
        String json = obterDados(endereco);
        try {
            return JsonMapper.parseJsonParaObjeto(json);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao converter JSON para objeto Java", e);
        }
    }
}
