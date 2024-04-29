package com.github.brunobuttros.userscore.service;

import com.github.brunobuttros.userscore.dto.EnderecoDTO;
import com.github.brunobuttros.userscore.dto.RegisterDTO;
import com.github.brunobuttros.userscore.dto.UserScoreDTO;
import com.github.brunobuttros.userscore.dto.UsuarioDTO;
import com.github.brunobuttros.userscore.entity.EnderecoEntity;
import com.github.brunobuttros.userscore.entity.UsuarioEntity;
import com.github.brunobuttros.userscore.exceptions.UsuarioNotFoundException;
import com.github.brunobuttros.userscore.repository.EnderecoRepository;
import com.github.brunobuttros.userscore.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final BuscaCepClient buscaCepClient;
    private final ScoreApiClient scoreApiClient;
    private final EnderecoRepository enderecoRepository;
    
    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository,
                          BuscaCepClient buscaCepClient,
                          ScoreApiClient scoreApiClient,
                          EnderecoRepository enderecoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.buscaCepClient = buscaCepClient;
        this.scoreApiClient = scoreApiClient;
        this.enderecoRepository = enderecoRepository;
    }

    public ResponseEntity register(RegisterDTO data) {
        if (usuarioRepository.existsByLogin(data.login()) || usuarioRepository.existsByCpf(data.cpf())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário com login ou CPF já existente.");
        }

        int score = scoreApiClient.getScore(data.cpf());
        EnderecoEntity enderecoEntity = buscaCepClient.buscarEnderecoPorCep(data.cep());

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        UsuarioEntity novoUsuario = new UsuarioEntity(data.login(), encryptedPassword, data.role());
        novoUsuario.setNome(data.nome());
        novoUsuario.setEmail(data.email());
        novoUsuario.setCpf(data.cpf());
        novoUsuario.setTelefone(data.telefone());
        novoUsuario.setCep(data.cep());
        novoUsuario.setScore(score);
        novoUsuario.setEndereco(enderecoEntity);

        UsuarioEntity usuarioSalvo = this.usuarioRepository.save(novoUsuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioSalvo);
    }

    public UsuarioEntity atualizarUsuario(Long id, UsuarioDTO usuarioDTO) {
;
        UsuarioEntity usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com o ID: " + id));

        if (usuarioDTO.email() != null && !usuarioExistente.getEmail().equals(usuarioDTO.email())) {
            usuarioExistente.setEmail(usuarioDTO.email());
        }
        if (!usuarioExistente.getTelefone().equals(usuarioDTO.telefone()) && !usuarioDTO.telefone().isBlank()) {
            usuarioExistente.setTelefone(usuarioDTO.telefone());
        }
        if (!usuarioExistente.getCep().equals(usuarioDTO.cep())&& Objects.nonNull(usuarioDTO.cep())) {
            EnderecoEntity enderecoExistente = enderecoRepository.findByCep(usuarioDTO.cep());
            if (enderecoExistente != null) {
                usuarioExistente.setEndereco(enderecoExistente);
            } else {
                EnderecoEntity enderecoEntity = buscaCepClient.buscarEnderecoPorCep(usuarioDTO.cep());
                usuarioExistente.setEndereco(enderecoEntity);
            }
            usuarioExistente.setCep(usuarioDTO.cep());
        }

        int score = scoreApiClient.getScore(usuarioDTO.cpf());
        usuarioExistente.setScore(score);

        usuarioRepository.save(usuarioExistente);
        return usuarioExistente;
    }

    public List<UsuarioEntity> buscarUsuarios(Long id, String nome, String email, String telefone, String cpf) {
        if (id != null) {
            return Collections.singletonList(usuarioRepository.findById(id)
                    .orElseThrow(() -> new UsuarioNotFoundException("Usuário não encontrado com o ID: " + id)));
        } else if (nome != null) {
            return usuarioRepository.findByNome(nome);
        } else if (email != null) {
            return usuarioRepository.findByEmail(email);
        } else if (telefone != null) {
            return usuarioRepository.findByTelefone(telefone);
        } else if (cpf != null) {
            return usuarioRepository.findByCpf(cpf);
        } else {
            return usuarioRepository.findAll();
        }
    }

    public void deletarUsuario(Long id) {
        UsuarioEntity usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com o ID: " + id));

        usuarioRepository.delete(usuarioExistente);
    }

    public UserScoreDTO getUserScoreById(Long id) {
        UsuarioEntity usuarioEntity = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com o ID: " + id));

        int score = scoreApiClient.getScore(usuarioEntity.getCpf());

        return new UserScoreDTO(usuarioEntity.getId(), score);
    }

    private EnderecoEntity buscaEnderecoPorCep(String cep) {
        return buscaCepClient.buscarEnderecoPorCep(cep);
    }

    private int obterScorePorCpf(String cpf) {
        return scoreApiClient.getScore(cpf);
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
}