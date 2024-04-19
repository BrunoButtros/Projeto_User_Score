package com.github.brunobuttros.userscore.dto;

import com.github.brunobuttros.userscore.entity.UsuarioRole;

public record RegisterDTO(String login, String password, UsuarioRole role) {
}
