package br.com.alura.leitura.repository;

import br.com.alura.leitura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {

    Optional<Autor> findByNomeIgnoreCase(String nome);

    List<Autor> findByAnoNascimentoLessThanEqualAndAnoFalecimentoIsNullOrAnoFalecimentoGreaterThan(Integer anoNascimentoLimite, Integer anoFalecimentoLimite);

    default List<Autor> findAutoresVivosEm(int ano) {
        return findByAnoNascimentoLessThanEqualAndAnoFalecimentoIsNullOrAnoFalecimentoGreaterThan(ano, ano);
    }
}
