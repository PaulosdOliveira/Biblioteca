package com.example.biblioteca.application.emprestimo;

import com.example.biblioteca.model.emprestimo.CadastroEmprestimoDTO;
import com.example.biblioteca.model.emprestimo.LivroEmprestadoDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/emprestimo")
public class LivrosEmprestadosController {


    @Autowired
    private LivroEmprestadoService service;


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void emprestarLivro(@RequestBody @Valid CadastroEmprestimoDTO dadosEmprestimo) {
        service.emprestatrLivro(dadosEmprestimo);
    }

    @GetMapping(params = "idCliente")
    public List<LivroEmprestadoDTO> getLivrosByCpf(@RequestParam UUID idCliente) {
        return service.getLivrosEmprestadosByCpf(idCliente);
    }

    @PutMapping("/{id}")
    public void marcarComoDevolvido(@PathVariable UUID id) {
        service.marcarComoDevolvido(id);
    }
}
