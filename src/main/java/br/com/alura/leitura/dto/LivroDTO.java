package br.com.alura.leitura.dto;

import br.com.alura.leitura.model.Livro;

public class LivroDTO {
    private Long id;
    private String titulo;
    private String primeiroAutor;
    private String primeiroIdioma;
    private int numeroDownloads;

    public LivroDTO(Livro livro) {
        this.id = livro.getId();
        this.titulo = livro.getTitulo();
        this.primeiroAutor = livro.getPrimeiroAutor();
        this.primeiroIdioma = livro.getPrimeiroIdioma();
        this.numeroDownloads = livro.getNumeroDownloads();
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

    public String getPrimeiroAutor() {
        return primeiroAutor;
    }

    public void setPrimeiroAutor(String primeiroAutor) {
        this.primeiroAutor = primeiroAutor;
    }

    public String getPrimeiroIdioma() {
        return primeiroIdioma;
    }

    public void setPrimeiroIdioma(String primeiroIdioma) {
        this.primeiroIdioma = primeiroIdioma;
    }

    public int getNumeroDownloads() {
        return numeroDownloads;
    }

    public void setNumeroDownloads(int numeroDownloads) {
        this.numeroDownloads = numeroDownloads;
    }
}