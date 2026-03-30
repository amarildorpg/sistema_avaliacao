package br.com.datanorte.sistemas_avaliacao.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void enviarAtivacao(String destinatario, String token) {
        String link = "http://localhost:8080/users/ativar?token=" + token;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(destinatario);
        message.setSubject("Ativação de conta - Sistemas Avaliação");
        message.setText("""
                Olá!
                
                Para ativar sua conta, clique no link abaixo:
                
                %s
                
                O link expira em 2 horas.
                
                Caso não tenha solicitado, ignore este e-mail.
                """.formatted(link));

        mailSender.send(message);
    }

    public void enviarRecuperacaoSenha(String destinatario, String token) {
        String link = "http://localhost:8080/users/recuperar-senha?token=" + token;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(destinatario);
        message.setSubject("Recuperação de senha - Sistemas Avaliação");
        message.setText("""
                Olá!
                
                Para redefinir sua senha, clique no link abaixo:
                
                %s
                
                O link expira em 2 horas.
                
                Caso não tenha solicitado, ignore este e-mail.
                """.formatted(link));

        mailSender.send(message);
    }
}