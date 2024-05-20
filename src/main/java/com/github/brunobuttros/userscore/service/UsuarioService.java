package com.github.brunobuttros.userscore.service;

import com.github.brunobuttros.userscore.dto.CadastrarDTO;
import com.github.brunobuttros.userscore.dto.EnderecoDTO;
import com.github.brunobuttros.userscore.dto.UserScoreDTO;
import com.github.brunobuttros.userscore.dto.UsuarioDTO;
import com.github.brunobuttros.userscore.entity.EnderecoEntity;
import com.github.brunobuttros.userscore.entity.UsuarioEntity;
import com.github.brunobuttros.userscore.exceptions.UsuarioNotFoundException;
import com.github.brunobuttros.userscore.repository.EnderecoRepository;
import com.github.brunobuttros.userscore.repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {
    private static final Logger logger = LoggerFactory.getLogger(UsuarioService.class);

    private final UsuarioRepository usuarioRepository;
    private final BuscaCepClient buscaCepClient;
    private final ScoreApiClient scoreApiClient;
    private final EnderecoRepository enderecoRepository;


    public UsuarioService(UsuarioRepository usuarioRepository,
                          BuscaCepClient buscaCepClient,
                          ScoreApiClient scoreApiClient,
                          EnderecoRepository enderecoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.buscaCepClient = buscaCepClient;
        this.scoreApiClient = scoreApiClient;
        this.enderecoRepository = enderecoRepository;
    }

    public UsuarioEntity cadastrar(CadastrarDTO data) {
        if (usuarioRepository.existsByLogin(data.login()) || usuarioRepository.existsByCpf(data.cpf())) {
            throw new IllegalArgumentException("Usuário com login ou CPF já existente.");
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

        return usuarioRepository.save(novoUsuario);
    }

    public UsuarioEntity atualizarUsuario(Long id, UsuarioDTO usuarioDTO) {
;
        UsuarioEntity usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNotFoundException("Usuário não encontrado com o ID: " + id));

        if (usuarioDTO.email() != null && !usuarioDTO.email().equals(usuarioExistente.getEmail())) {
            usuarioExistente.setEmail(usuarioDTO.email());
        }
        if (usuarioDTO.telefone() != null && !usuarioDTO.telefone().equals(usuarioExistente.getTelefone())) {
            usuarioExistente.setTelefone(usuarioDTO.telefone());
        }
        if (usuarioDTO.cep() != null && !usuarioDTO.cep().equals(usuarioExistente.getCep())) {
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

    public List<UsuarioDTO> buscarUsuarios(Long id, String nome, String email, String telefone, String cpf) {
        logger.info("Buscando usuários com os parâmetros - id: {}, nome: {}, email: {}, telefone: {}, cpf: {}", id, nome, email, telefone, cpf);

        List<UsuarioEntity> entities;

        if (id == null && nome == null && email == null && telefone == null && cpf == null) {
            entities = usuarioRepository.findAll();
        } else {
            entities = usuarioRepository.findByIdOrNomeOrEmailOrTelefoneOrCpf(id, nome, email, telefone, cpf);
        }
        logger.info("Número de usuários encontrados: {}", entities.size());

        return entities.stream()
                .map(this::convertEntityToDTO)
                .collect(Collectors.toList());
    }

    public void deletarUsuario(Long id) {
        UsuarioEntity usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNotFoundException("Usuário não encontrado com o ID: " + id));

        usuarioRepository.delete(usuarioExistente);
    }

    public UserScoreDTO getUserScoreById(Long id) {
        UsuarioEntity usuarioEntity = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNotFoundException("Usuário não encontrado com o ID: " + id));

        int score = scoreApiClient.getScore(usuarioEntity.getCpf());

        return new UserScoreDTO(usuarioEntity.getId(), score);
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