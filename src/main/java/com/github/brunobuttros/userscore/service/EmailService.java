package com.github.brunobuttros.userscore.service;

import com.github.brunobuttros.userscore.config.EmailConfig;
import com.github.brunobuttros.userscore.entity.UsuarioEntity;
import com.github.brunobuttros.userscore.repository.UsuarioRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
public class EmailService {

    @Autowired
    private EmailConfig emailConfig;

    @Autowired
    private JavaMailSender emailSender;
    @Autowired
    private UsuarioRepository usuarioRepository;


    public void enviarDocumentoParaAdmins(String caminhoDoDocumento) {
        List<UsuarioEntity> admins = usuarioRepository.findByRole("ADMIN");

        for (UsuarioEntity admin : admins) {
            enviarEmail(admin.getEmail(), "Relatório", "ID e Score de cada usuário cadastrado.", caminhoDoDocumento);
        }
    }

    private void enviarEmail(String emailDestino, String assunto, String texto, String caminhoDoAnexo) {
        MimeMessage mensagem = emailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(mensagem, true);
            helper.setFrom(emailConfig.getUsername());
            helper.setTo(emailDestino);
            helper.setSubject(assunto);
            helper.setText(texto);

            File anexo = new File(caminhoDoAnexo);
            helper.addAttachment("usuario_score.txt", anexo);

            emailSender.send(mensagem);
        } catch (MessagingException e) {
            System.err.println("Erro ao enviar e-mail: " + e.getMessage());
        }
    }
}
