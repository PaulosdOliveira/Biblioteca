package com.example.biblioteca.application.usuario;

import com.example.biblioteca.application.email.CodigoValidacaoService;
import com.example.biblioteca.application.ex.exception.EmailDuplicadoException;
import com.example.biblioteca.application.ex.exception.EmailNaoValidadoException;
import com.example.biblioteca.application.jwt.JwtService;
import com.example.biblioteca.model.usuario.CadastroUsuarioDTO;
import com.example.biblioteca.model.usuario.LoginUsuarioDTO;
import com.example.biblioteca.model.usuario.Usuario;
import com.example.biblioteca.model.usuario.codigo.CodigoValidacaoDTO;
import com.example.biblioteca.model.usuario.enums.Perfil;
import com.example.biblioteca.repository.UsuarioRepository;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Data
@Service
public class UsuarioService {

    private final UsuarioRepository repository;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;
    private final CodigoValidacaoService codigoValidacaoService;


    public void cadastrarUsuario(CadastroUsuarioDTO dadosCadastrais) {
        String email = dadosCadastrais.getEmail();
        var usuarioOptional = repository.findByEmail(email);
        // CASO NÃO TENHA USUÁRIO SALVO NO BANCO DE DADOS
        // SALVAR E ENVIAR CÓDIGO
        if (usuarioOptional.isEmpty()) {
            var usuario = new Usuario(dadosCadastrais);
            usuario.setSenha(encoder.encode(dadosCadastrais.getSenha()));
            repository.save(usuario);
        } // CASO JA TENHA UM USUÁRIO ATIVO
        else {
            throw new EmailDuplicadoException();
        }
        // ENVIANDO CÓDIGO AO EMAIL DO USUÁRIO
        codigoValidacaoService.gerarCodigo(email, dadosCadastrais.getNome());
    }

    // CADASTRANDO ADM
    public void cadastrarADM(CadastroUsuarioDTO dadosCadastrais) {
        String email = dadosCadastrais.getEmail();
        var usuarioOptional = repository.findByEmail(email);
        if (usuarioOptional.isEmpty()) {
            var usuario = new Usuario(dadosCadastrais, Perfil.ADM);
            usuario.setSenha(encoder.encode(dadosCadastrais.getSenha()));
            repository.save(usuario);
        } // CASO JA TENHA UM USUÁRIO ATIVO
        else if (usuarioOptional.get().isAtivo()) {
            throw new EmailDuplicadoException();
        }
        // ENVIANDO CÓDIGO AO EMAIL DO USUÁRIO
        codigoValidacaoService.gerarCodigo(email, dadosCadastrais.getNome());
    }

    // VALIDANDO CÓDIGO DO USUÁRIO
    @Transactional
    public ResponseEntity<?> validarUsuario(CodigoValidacaoDTO validacaoDTO) {
        var usuario = repository.findByEmail(validacaoDTO.getEmail()).orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
        if (usuario.isAtivo())
            return ResponseEntity.status(HttpStatus.CONFLICT.value()).body("Seu email já foi validado");
        codigoValidacaoService.validarCodigo(validacaoDTO);
        usuario.setAtivo(true);
        return ResponseEntity.ok().body("Token: " + jwtService.getToken(usuario));
    }


    // SOLICITANDO NOVO CÓDICO
    public void solicitarCodigo(String emailDestino) {
        Usuario usuario = repository.buscarPorEmail(emailDestino).orElseThrow();
        if (usuario.isAtivo()) throw new RuntimeException("Seu email já foi confirmado");
        codigoValidacaoService.gerarCodigo(emailDestino, usuario.getNome());
    }

    public String logarUsuario(LoginUsuarioDTO loginDTO) {
        var usuario = findByEmail(loginDTO.getEmail());
        if (usuario != null) {
            if (!usuario.isAtivo()) throw new EmailNaoValidadoException();
            var senhaBate = encoder.matches(loginDTO.getSenha(), usuario.getSenha());
            if (senhaBate) {
                String token = jwtService.getToken(usuario);
                return token;
            }
            throw new UsernameNotFoundException("Email e/ou senha incorretos");
        }
        throw new UsernameNotFoundException("Usuário não encontrado");
    }

    public Usuario findByEmail(String email) {
        return repository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
    }
}
