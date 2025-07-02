package br.com.alura.leitura.model;

import com.fasterxml.jackson.annotation.JsonAlias;

public record DadosLivro(
        @JsonAlias("id") String id,
        @JsonAlias("title") String titulo,
        @JsonAlias("authors") String autor
) {
}
