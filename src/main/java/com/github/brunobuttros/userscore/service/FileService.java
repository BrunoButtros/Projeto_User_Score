package com.github.brunobuttros.userscore.service;

import com.github.brunobuttros.userscore.dto.UsuarioDTO;
import com.github.brunobuttros.userscore.entity.UsuarioEntity;
import com.github.brunobuttros.userscore.exceptions.FileCreationException;
import com.github.brunobuttros.userscore.repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileService {
    private static final Logger logger = LoggerFactory.getLogger(FileService.class);

    private final UsuarioRepository usuarioRepository;
    private final ScoreApiClient scoreApiClient;
    private final EmailService emailService;

    @Value("${feature.envia.email}")
    private boolean flagMail;


    public FileService(UsuarioRepository usuarioRepository, UsuarioService usuarioService, ScoreApiClient scoreApiClient, EmailService emailService) {
        this.usuarioRepository = usuarioRepository;
        this.scoreApiClient = scoreApiClient;
        this.emailService = emailService;
    }

    public void criarArquivoUsuario(List<UsuarioDTO> usuarios) {
        String nomeArquivo = "usuarios_scores.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeArquivo))) {
            for (UsuarioDTO usuario : usuarios) {
                writer.write("ID: " + usuario.id() + ", score atual: " + usuario.score());
                writer.newLine();
            }
        } catch (IOException e) {
            throw new FileCreationException("Falha ao criar o arquivo '" + nomeArquivo + "'", e);
        }
    }

    @Scheduled(fixedDelay = 500000)
    public void atualizarArquivoUsuariosPeriodicamente() {
        String nomeArquivo = "usuarios_scores.txt";
        List<UsuarioDTO> usuarios = new ArrayList<>();
        List<UsuarioEntity> usuariosFromRepository = usuarioRepository.findAll();

        for (UsuarioEntity usuario : usuariosFromRepository) {
            int novoScore = scoreApiClient.getScore(usuario.getCpf());

            usuario.setScore(novoScore);
            usuarioRepository.save(usuario);

            usuarios.add(new UsuarioDTO(usuario.getId(), null, // nome
                    null, // email
                    null, // telefone
                    null, // cpf
                    null, // cep
                    novoScore, // usar o novo score gerado
                    null // endereco
            ));
        }

        criarArquivoUsuario(usuarios);

        if (flagMail) {
            String caminhoDoDocumento = "C:\\Users\\bruno\\Desktop\\user-score\\usuarios_scores.txt";
            emailService.enviarDocumentoParaAdmins(caminhoDoDocumento);
        }
    }
}