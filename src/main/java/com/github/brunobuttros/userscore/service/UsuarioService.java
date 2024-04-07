package com.github.brunobuttros.userscore.service;

import com.github.brunobuttros.userscore.dto.EnderecoDTO;
import com.github.brunobuttros.userscore.dto.UsuarioDTO;
import com.github.brunobuttros.userscore.entity.EnderecoEntity;
import com.github.brunobuttros.userscore.entity.UsuarioEntity;
import com.github.brunobuttros.userscore.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final BuscaCep buscaCep;
    private final ScoreApiClient scoreApiClient;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository, BuscaCep buscaCep, ScoreApiClient scoreApiClient) {
        this.usuarioRepository = usuarioRepository;
        this.buscaCep = buscaCep;
        this.scoreApiClient = scoreApiClient;
    }

    public UsuarioEntity cadastrarUsuario(UsuarioDTO usuarioDTO) {
        int score = scoreApiClient.getScore(usuarioDTO.cpf());

        EnderecoEntity enderecoEntity = buscaCep.buscarEnderecoPorCep(usuarioDTO.cep());

        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setNome(usuarioDTO.nome());
        usuarioEntity.setEmail(usuarioDTO.email());
        usuarioEntity.setTelefone(usuarioDTO.telefone());
        usuarioEntity.setCpf(usuarioDTO.cpf());
        usuarioEntity.setCep(usuarioDTO.cep());
        usuarioEntity.setScore(score);
        usuarioEntity.setEndereco(enderecoEntity);

        return usuarioRepository.save(usuarioEntity);
    }

    public UsuarioDTO convertEntityToDTO(UsuarioEntity usuarioEntity) {
        EnderecoEntity endereco = usuarioEntity.getEndereco();
        return new UsuarioDTO(
                usuarioEntity.getId(),
                usuarioEntity.getNome(),
                usuarioEntity.getEmail(),
                usuarioEntity.getTelefone(),
                usuarioEntity.getCpf(),
                endereco != null ? endereco.getCep() : null,
                usuarioEntity.getScore(),
                endereco != null ? convertEnderecoToDTO(endereco) : null
        );
    }

    private EnderecoDTO convertEnderecoToDTO(EnderecoEntity enderecoEntity) {
        return new EnderecoDTO(
                enderecoEntity.getId(),
                enderecoEntity.getCep(),
                enderecoEntity.getLogradouro(),
                enderecoEntity.getBairro(),
                enderecoEntity.getLocalidade(),
                enderecoEntity.getUf()
        );
    }

    private EnderecoEntity buscaEnderecoPorCep(String cep) {
        return buscaCep.buscarEnderecoPorCep(cep);
    }

    private int obterScorePorCpf(String cpf) {
        return scoreApiClient.getScore(cpf);
    }
}
