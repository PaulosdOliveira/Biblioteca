package com.example.biblioteca.application.cliente;

import com.example.biblioteca.model.cliente.CadastroClienteDTO;
import com.example.biblioteca.model.cliente.Cliente;
import com.example.biblioteca.model.cliente.UpdateClienteDTO;
import com.example.biblioteca.repository.ClienteRepository;
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

    public void deletarCliente(UUID id) {

    }

    public List<Cliente> findAll() {
        return repository.findAll();
    }
}
