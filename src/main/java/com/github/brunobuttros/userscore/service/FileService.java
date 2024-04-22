package com.github.brunobuttros.userscore.service;

import com.github.brunobuttros.userscore.dto.UsuarioDTO;
import com.github.brunobuttros.userscore.entity.UsuarioEntity;
import com.github.brunobuttros.userscore.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioService usuarioService;
    private final ScoreApiClient scoreApiClient;


    @Autowired
    public FileService(UsuarioRepository usuarioRepository, UsuarioService usuarioService, ScoreApiClient scoreApiClient) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioService = usuarioService;
        this.scoreApiClient = scoreApiClient;
    }

    public void criarArquivoUsuario(List<UsuarioDTO> usuarios) {
        String nomeArquivo = "usuarios_scores.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeArquivo))) {
            for (UsuarioDTO usuario : usuarios) {
                writer.write("ID: " + usuario.id() + ", score atual: " + usuario.score());
                writer.newLine();
            }
            System.out.println("Arquivo: '" + nomeArquivo + "' criado com sucesso!");

            // emailService.enviarArquivoPorEmail(nomeArquivo);

        } catch (IOException e) {
            System.err.println("Falha ao criar o arquivo" + e.getMessage());
        }
    }

    @Scheduled(fixedDelay = 50000)
    public void atualizarArquivoUsuariosPeriodicamente() {
        try {
            String nomeArquivo = "usuarios_scores.txt";
            File arquivo = new File(nomeArquivo);
            if (arquivo.exists()) {
                boolean deletado = arquivo.delete();
                if (!deletado) {
                    System.err.println("Falha ao excluir o arquivo " + nomeArquivo);
                } else {
                    System.out.println("Arquivo deletado com sucesso");
                }
            }
            List<UsuarioDTO> usuarios = new ArrayList<>();
            List<UsuarioEntity> usuariosFromRepository = usuarioRepository.findAll();

            for (UsuarioEntity usuario : usuariosFromRepository) {
                int novoScore = scoreApiClient.getScore(usuario.getCpf());

                // Atualizar o score do usuário
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
        } catch (Exception e) {
            System.err.println("Falha ao atualizar os usuários: " + e.getMessage());
        }
    }
}