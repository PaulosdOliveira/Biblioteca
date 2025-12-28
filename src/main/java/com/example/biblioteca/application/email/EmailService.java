package com.example.biblioteca.application.email;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
@Service
public class EmailService {


    private final JavaMailSender mailSender;

    private final ResourceLoader resourceLoader;

    @Async
    public void enviarEmail(String para, String nome, String codigo) {

        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
            helper.setTo(para);
            helper.setSubject("Valide seu email");
            helper.setText(carregarHTML(nome, codigo), true);
            mailSender.send(mimeMessage);
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    private String carregarHTML(String nome, String codigo) throws IOException {
        Resource resource = resourceLoader.getResource("classpath:templates/templete-email.html");
        String html = new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
        return html.replace("{{nome}}", nome)
                .replace("{{codigo}}", codigo);
    }

}
