package com.example.biblioteca.application.usuario;

import com.example.biblioteca.model.usuario.CadastroUsuarioDTO;
import com.example.biblioteca.model.usuario.EmailUsuario;
import com.example.biblioteca.model.usuario.LoginUsuarioDTO;
import com.example.biblioteca.model.usuario.MyUserDetails;
import com.example.biblioteca.model.usuario.codigo.CodigoValidacaoDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public String cadastrarUsuario(@RequestBody @Valid CadastroUsuarioDTO dadosCadastrais) {
        service.cadastrarUsuario(dadosCadastrais);
        return "Um código de válidação foi enviado ao seu email, use-o para ativar sua conta";
    }

    //@PreAuthorize("hasrole('ADM')")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/adm")
    public String cadastrarAdm(@RequestBody @Valid CadastroUsuarioDTO dadosCadastrais) {
        service.cadastrarADM(dadosCadastrais);
        return "Um código de válidação foi enviado ao seu email, use-o para ativar sua conta";
    }

    @PutMapping("/validar")
    public ResponseEntity<?> validarEmail(@RequestBody CodigoValidacaoDTO validacaoDTO) {
        return service.validarUsuario(validacaoDTO);
    }


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/solicitar-codigo")
    public void solicitarCodigo(@RequestBody EmailUsuario emailUsuario) {
        service.solicitarCodigo(emailUsuario.email());
    }


    @PostMapping("/login")
    public String getToken(@RequestBody @Valid LoginUsuarioDTO loginDTO) {
        return "Token: " + service.logarUsuario(loginDTO);
    }


    @GetMapping
    public String getStatus() {
        assert SecurityContextHolder.getContext().getAuthentication() != null;
        MyUserDetails seila = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        assert seila != null;
        return """
                       Bom dia!!!!
                       """ + seila.getNome();
    }


}
