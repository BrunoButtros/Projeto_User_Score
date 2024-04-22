package com.github.brunobuttros.userscore.dto;

import com.github.brunobuttros.userscore.entity.UsuarioRole;

public record RegisterDTO(
        String login,
        String password,
        String email,
        String nome,
        String cpf,
        String telefone,
        String cep,
        UsuarioRole role
) {
}
