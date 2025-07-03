package br.com.alura.leitura.repository;

import br.com.alura.leitura.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LivroRepository extends JpaRepository<Livro, Long> {

    Optional<Livro> findFirstByTituloIgnoreCase(String titulo);

    @Query("SELECT DISTINCT l FROM Livro l JOIN FETCH l.autores JOIN FETCH l.idiomas i WHERE i = :idioma")
    List<Livro> findByIdiomaComAutoresEIdiomas(@Param("idioma") String idioma);

    @Query("SELECT DISTINCT l FROM Livro l JOIN FETCH l.autores JOIN FETCH l.idiomas")
    List<Livro> findAllComAutoresEIdiomas();

    @Query("SELECT COUNT(l) FROM Livro l JOIN l.idiomas i WHERE i = :idioma")
    long contarPorIdioma(@Param("idioma") String idioma);

    @Query("SELECT DISTINCT l FROM Livro l JOIN FETCH l.autores JOIN FETCH l.idiomas WHERE l.anoLancamento = :ano")
    List<Livro> findByAnoLancamentoComAutoresEIdiomas(@Param("ano") Integer ano);

    List<Livro> findByAnoLancamento(int ano);
}
