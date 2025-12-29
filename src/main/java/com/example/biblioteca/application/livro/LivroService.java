package com.example.biblioteca.application.livro;

import com.example.biblioteca.model.livro.CadastroLivroDTO;
import com.example.biblioteca.model.livro.Livro;
import com.example.biblioteca.repository.LivroRepository;
import com.example.biblioteca.validation.LivroValidator;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class LivroService {


    @Autowired
    private LivroRepository repository;

    @Autowired
    private LivroValidator validator;


    public void salvarLivro(CadastroLivroDTO dadosCadastrais) {
        validator.validar(dadosCadastrais.getTitulo(), dadosCadastrais.getAutor());
        repository.save(new Livro(dadosCadastrais));
    }

    @Transactional
    public void editarDadosLivro(UUID id, CadastroLivroDTO novosDados) {
        var livro = repository.findById(id).orElseThrow();
        if (StringUtils.isNotBlank(novosDados.getAutor())) livro.setAutor(novosDados.getAutor());
        if (StringUtils.isNotBlank(novosDados.getTitulo())) livro.setTitulo(novosDados.getTitulo());
        if (novosDados.getQtdDisponivel() != null) livro.setQtdDisponivel(novosDados.getQtdDisponivel());
    }

    public void deletarLivro(UUID id) {
        repository.deleteById(id);
    }


    public List<Livro> buscarLivros() {
        return repository.findAll();
    }

    public Livro findById(UUID id) {
        return repository.findById(id).orElseThrow();
    }
}
