package com.example.biblioteca.application.cliente;

import com.example.biblioteca.model.cliente.CadastroClienteDTO;
import com.example.biblioteca.model.cliente.Cliente;
import com.example.biblioteca.model.cliente.UpdateClienteDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private ClienteService service;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void cadastrarCliente(@RequestBody @Valid CadastroClienteDTO dadosCadastrais) {
        service.cadastrarCliente(dadosCadastrais);
    }

    // HttpMessageNotReadableException

    @PutMapping("/{id}")
    public void alterarDadosCliente(@PathVariable UUID id, @RequestBody UpdateClienteDTO novosDados) {
        service.alterarDadosCliente(id, novosDados);
    }

    @GetMapping
    public List<Cliente> getClientes(){
        return service.findAll();
    }
}
