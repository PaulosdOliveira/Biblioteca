package com.example.biblioteca.application.emprestimo;

import com.example.biblioteca.application.cliente.ClienteService;
import com.example.biblioteca.application.livro.LivroService;
import com.example.biblioteca.model.cliente.Cliente;
import com.example.biblioteca.model.emprestimo.CadastroEmprestimoDTO;
import com.example.biblioteca.model.emprestimo.LivroEmprestadoDTO;
import com.example.biblioteca.model.emprestimo.LivrosEmprestados;
import com.example.biblioteca.model.emprestimo.enums.Status;
import com.example.biblioteca.model.livro.Livro;
import com.example.biblioteca.repository.LivrosEmprestadosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class LivroEmprestadoService {


    @Autowired
    private  LivrosEmprestadosRepository repository;

    @Autowired
    private LivroService livroService;

    @Autowired
    private ClienteService clienteService;


    @Transactional
    public void emprestatrLivro(CadastroEmprestimoDTO dto) {
        if (!clienteService.existsById(dto.getIdCliente())) throw new RuntimeException("Cliente não encontrado");
        var livro = livroService.findById(dto.getIdLivro());
        var livrosEmprestado = new LivrosEmprestados(livro, new Cliente(dto.getIdCliente()));
        repository.save(livrosEmprestado);
        livro.setQtdDisponivel(livro.getQtdDisponivel() - 1);
    }

    @Transactional
    public void marcarComoDevolvido(UUID id) {
        var emprestimo = repository.findById(id).orElseThrow();
        emprestimo.setStatus(Status.ENTREGUE);
        Livro livro = emprestimo.getLivro();
        livro.setQtdDisponivel(livro.getQtdDisponivel() + 1);
    }

    public List<LivroEmprestadoDTO> getLivrosEmprestadosByCpf(UUID id) {
        return repository.getLivrosEmprestadosByIdCliente(id);
    }
}
