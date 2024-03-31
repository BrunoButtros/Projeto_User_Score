package com.github.brunobuttros.userscore.dto;

import java.util.ArrayList;
import java.util.List;

public record UsuarioDTO(Long id, String nome, String email, String telefone, String cpf, int score,
                         List<EnderecoDTO> enderecos) {
    public UsuarioDTO {
        enderecos = new ArrayList<>();
    }
}
