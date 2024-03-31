package com.github.brunobuttros.userscore.service;

import com.github.brunobuttros.userscore.entity.UsuarioEntity;
import com.github.brunobuttros.userscore.exceptions.UsuarioNotFoundException;
import com.github.brunobuttros.userscore.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ScoreService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public void atualizarScore(Long userId, int novoScore) {
        Optional<UsuarioEntity> optionalUsuario = usuarioRepository.findById(userId);
        if (optionalUsuario.isPresent()) {
            UsuarioEntity usuario = optionalUsuario.get();
            usuario.setScore(novoScore);
            usuarioRepository.save(usuario);
        } else {
            throw new UsuarioNotFoundException("Usuário não encontrado com o ID: " + userId);
        }
    }
}