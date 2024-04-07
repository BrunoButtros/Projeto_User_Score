package com.github.brunobuttros.userscore.service;

import com.github.brunobuttros.userscore.dto.UsuarioDTO;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Service
public class FileService {

    public void criarArquivoUsuario(List<UsuarioDTO> usuarios) {
        String nomeArquivo = "usuarios_scores.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeArquivo))) {
            for (UsuarioDTO usuario : usuarios) {
                writer.write("ID: " + usuario.id() + ", score atual: " + usuario.score());
                writer.newLine();
            }
            System.out.println("Arquivo: '" + nomeArquivo + "' criado com sucesso!");

        } catch (IOException e) {
            System.err.println("Falhar ao criar o arquivo" + e.getMessage());
        }
    }

}
