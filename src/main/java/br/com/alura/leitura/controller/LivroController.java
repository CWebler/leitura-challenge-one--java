package br.com.alura.leitura.controller;

import br.com.alura.leitura.dto.EstatisticaIdiomaDTO;
import br.com.alura.leitura.service.LivroService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/livros")
public class LivroController {

    private final LivroService livroService;

    public LivroController(LivroService livroService) {
        this.livroService = livroService;
    }

    @GetMapping("/estatisticas/idioma/{idioma}")
    public EstatisticaIdiomaDTO contarLivrosPorIdioma(@PathVariable String idioma) {
        return livroService.contarPorIdioma(idioma);
    }
}