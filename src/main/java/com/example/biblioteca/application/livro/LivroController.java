package com.example.biblioteca.application.livro;

import com.example.biblioteca.model.livro.CadastroLivroDTO;
import com.example.biblioteca.model.livro.Livro;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/livro")
public class LivroController {

    @Autowired
    private LivroService service;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void cadastrarLivro(@RequestBody @Valid CadastroLivroDTO dadosCadastrais) {
        service.salvarLivro(dadosCadastrais);
    }

    @PutMapping("/{id}")
    public void alterarDadosLivro(@PathVariable UUID id, @RequestBody CadastroLivroDTO novosDados) {
        service.editarDadosLivro(id, novosDados);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deletarLivro(@PathVariable UUID id) {
        service.deletarLivro(id);
    }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<Livro> buscarLivros() {
        return service.buscarLivros();
    }
}
