package br.com.alura.leitura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "livros")
public class Livro {

    @Id
    @JsonAlias("id")
    private Long id;

    @JsonAlias("title")
    private String titulo;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "livro_autor",
            joinColumns = @JoinColumn(name = "livro_id"),
            inverseJoinColumns = @JoinColumn(name = "autor_id"))
    @JsonAlias("authors")
    private List<Autor> autores;

    @ElementCollection
    @CollectionTable(name = "livro_idiomas", joinColumns = @JoinColumn(name = "livro_id"))
    @Column(name = "idioma")
    @JsonAlias("languages")
    private List<String> idiomas;

    @JsonAlias("download_count")
    private int numeroDownloads;

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

    public List<Autor> getAutores() {
        return autores;
    }

    public void setAutores(List<Autor> autores) {
        this.autores = autores;
    }

    public List<String> getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(List<String> idiomas) {
        this.idiomas = idiomas;
    }

    public int getNumeroDownloads() {
        return numeroDownloads;
    }

    public void setNumeroDownloads(int numeroDownloads) {
        this.numeroDownloads = numeroDownloads;
    }

    public String getPrimeiroIdioma() {
        return (idiomas != null && !idiomas.isEmpty()) ? idiomas.get(0) : null;
    }

    public String getPrimeiroAutor() {
        return (autores != null && !autores.isEmpty()) ? autores.get(0).getNome() : null;
    }

    @Override
    public String toString() {
        return "Livro: " + titulo +
                " | Autor: " + getPrimeiroAutor() +
                " | Idioma: " + getPrimeiroIdioma() +
                " | Downloads: " + numeroDownloads;
    }
}
