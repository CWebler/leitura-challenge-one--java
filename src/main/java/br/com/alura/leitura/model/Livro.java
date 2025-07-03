package br.com.alura.leitura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "livros")
public class Livro {

    @Id
    @JsonAlias("id")
    private Long id;

    @JsonAlias("title")
    private String titulo;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "livro_autor",
            joinColumns = @JoinColumn(name = "livro_id"),
            inverseJoinColumns = @JoinColumn(name = "autor_id"))
    @JsonAlias("authors")
    private Set<Autor> autores = new HashSet<>();

    @ElementCollection
    @CollectionTable(name = "livro_idiomas", joinColumns = @JoinColumn(name = "livro_id"))
    @Column(name = "idioma")
    @JsonAlias("languages")
    private Set<String> idiomas = new HashSet<>();

    @JsonAlias("download_count")
    private int numeroDownloads;

    @JsonAlias("copyright_year")
    @Column(name = "ano_lancamento")
    private Integer anoLancamento;

    public Livro() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Set<Autor> getAutores() {
        return autores;
    }

    public void setAutores(Set<Autor> autores) {
        this.autores = autores;
    }

    public Set<String> getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(Set<String> idiomas) {
        this.idiomas = idiomas;
    }

    public int getNumeroDownloads() {
        return numeroDownloads;
    }

    public void setNumeroDownloads(int numeroDownloads) {
        this.numeroDownloads = numeroDownloads;
    }

    public Integer getAnoLancamento() {
        return anoLancamento;
    }

    public void setAnoLancamento(Integer anoLancamento) {
        this.anoLancamento = anoLancamento;
    }

    public String getPrimeiroIdioma() {
        return (idiomas != null && !idiomas.isEmpty()) ? idiomas.iterator().next() : null;
    }

    public String getPrimeiroAutor() {
        return (autores != null && !autores.isEmpty()) ? autores.iterator().next().getNome() : null;
    }

    @Override
    public String toString() {
        return "Livro: " + titulo +
                " | Autor: " + getPrimeiroAutor() +
                " | Idioma: " + getPrimeiroIdioma() +
                " | Ano: " + anoLancamento +
                " | Downloads: " + numeroDownloads;
    }
}
