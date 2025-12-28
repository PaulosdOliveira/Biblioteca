package com.example.biblioteca.application.email;

import com.example.biblioteca.application.ex.exception.CodigoExpiradoException;
import com.example.biblioteca.application.ex.exception.CodigoInvalidoException;
import com.example.biblioteca.model.usuario.codigo.CodigoValidacao;
import com.example.biblioteca.model.usuario.codigo.CodigoValidacaoDTO;
import com.example.biblioteca.repository.CodigoValidacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Random;

@Service
public class CodigoValidacaoService {

    @Autowired
    private CodigoValidacaoRepository repository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private EmailService emailService;

    // TEMPO EM MINUTOS PARA REFERENCIAR A VALIDADE DO CODIGO
    private static final long tempoValidade = 10;


    // GERA, SALVA E ENVIA CÓDIGO POR EMAIL
    public void gerarCodigo(String emailDestino, String nome) {
        var codigoSalvo = repository.findByEmailDestino(emailDestino).orElse(null);
        if (codigoSalvo == null || deveGerar(codigoSalvo.getExpiracao())) {
            String codigo =
                    gerarCaractere() + gerarCaractere()
                    + gerarCaractere() + gerarCaractere()
                    + gerarCaractere() + gerarCaractere();
            repository.save(new CodigoValidacao(encoder.encode(codigo), emailDestino, tempoValidade));
            emailService.enviarEmail(emailDestino, nome,  codigo);
        }
    }

    // DECIDE SE DEVE GERAR NOVO CODIGO CASO O ATUAL ESTEJA QUASE EXPIRADO
    private boolean deveGerar(LocalDateTime expiracao) {
        return ChronoUnit.MINUTES.between(expiracao.plusMinutes(-tempoValidade), LocalDateTime.now()) >= 9;
    }


    // GERANDO UM NÚMERO DE 0 À 9
    private String gerarCaractere() {
        Random random = new Random();
        return random.nextInt(0, 10) + "";
    }

    public void validarCodigo(CodigoValidacaoDTO codigoDTO) {
        CodigoValidacao codigo = repository.findByEmailDestino(codigoDTO.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
        var dataHoraAtual = LocalDateTime.now();
        var minutosPassados = ChronoUnit.MINUTES.between(codigo.getExpiracao().plusMinutes(-tempoValidade), dataHoraAtual);
        if (!encoder.matches(codigoDTO.getCodigo(), codigo.getCodigo())) throw new CodigoInvalidoException();
        // MUDAR PARA UMA EXCESSÃO PERSONALIZADS
        if (minutosPassados >= tempoValidade) throw new CodigoExpiradoException();
    }

}
