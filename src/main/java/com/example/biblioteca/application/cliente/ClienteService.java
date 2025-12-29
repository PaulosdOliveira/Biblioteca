package com.example.biblioteca.application.cliente;

import com.example.biblioteca.model.cliente.CadastroClienteDTO;
import com.example.biblioteca.model.cliente.Cliente;
import com.example.biblioteca.model.cliente.UpdateClienteDTO;
import com.example.biblioteca.repository.ClienteRepository;
import com.example.biblioteca.repository.LivrosEmprestadosRepository;
import com.example.biblioteca.validation.ClienteValidation;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.UUID;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repository;

    @Autowired
    private ClienteValidation validator;

    @Autowired
    private LivrosEmprestadosRepository livrosEmprestadosRepository;


    public void cadastrarCliente(CadastroClienteDTO dadosCadastrais) {
        validator.validar(dadosCadastrais.getCpf());
        repository.save(new Cliente(dadosCadastrais));
    }


    @Transactional
    public void alterarDadosCliente(UUID id, UpdateClienteDTO novosDados) {
        var cliente = repository.findById(id).orElseThrow();
        if (StringUtils.isNotBlank(novosDados.getNome())) cliente.setNome(novosDados.getNome());
        if (StringUtils.isNotBlank(novosDados.getEmail())) cliente.setEmail(novosDados.getEmail());
        if (StringUtils.isNotBlank(novosDados.getContato())) cliente.setContato(novosDados.getContato());
    }

    @Transactional
    public void deletarCliente(UUID id) {
        int livrosEmprestados = livrosEmprestadosRepository.countLivrosEmprestados(id);
        if (livrosEmprestados > 0)
            throw new RuntimeException("Esse cliente possúi " + livrosEmprestados + " livro(s) emprestados");
        livrosEmprestadosRepository.deleteByClienteId(id);
        repository.deleteById(id);
    }

    // USADO APENAS PELO ADM
    @Transactional
    public void deletarClienteComPendencias(UUID id) {
        livrosEmprestadosRepository.deleteByClienteId(id);
        repository.deleteById(id);
    }

    public List<Cliente> findAll() {
        return repository.findAll();
    }

    public boolean existsById(UUID id) {
        return repository.existsById(id);
    }
}
