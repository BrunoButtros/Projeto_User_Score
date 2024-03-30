package com.github.brunobuttros.userscore.service;

import com.github.brunobuttros.userscore.dto.EnderecoDTO;
import com.github.brunobuttros.userscore.dto.UsuarioDTO;
import com.github.brunobuttros.userscore.entity.EnderecoEntity;
import com.github.brunobuttros.userscore.entity.UsuarioEntity;
import com.github.brunobuttros.userscore.exceptions.CepInvalidoException;
import com.github.brunobuttros.userscore.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final BuscaCep buscaCep;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository, BuscaCep buscaCep) {
        this.usuarioRepository = usuarioRepository;
        this.buscaCep = buscaCep;
    }

    public UsuarioEntity cadastrarUsuario(UsuarioDTO usuarioDTO) {
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setNome(usuarioDTO.nome());
        usuario.setEmail(usuarioDTO.email());
        usuario.setTelefone(usuarioDTO.telefone());
        usuario.setCpf(usuarioDTO.cpf());
        usuario.setScore(usuarioDTO.score());

        List<String> ceps = usuarioDTO.enderecos().stream().map(EnderecoDTO::cep).toList();
        List<EnderecoEntity> enderecos = new ArrayList<>();
        for (String cep : ceps) {
            try {

                EnderecoEntity endereco = buscaCep.getEnderecoEntity(cep);
                enderecos.add(endereco);
            } catch (CepInvalidoException e) {
                System.err.println("CEP INVALIDO" + cep + ": " + e.getMessage());
            } catch (Exception e) {
                System.err.println("Erro ao buscar o CEP" + cep + ": " + e.getMessage());

            }

        }
        usuario.setEnderecos(enderecos);
        return usuarioRepository.save(usuario);
    }


    public UsuarioEntity atualizarUsuario(Long id, UsuarioDTO usuarioDTO) {
        // Busca o usuário pelo ID e, se não existir, lança uma exceção
        UsuarioEntity usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // Atualiza email e telefone
        usuarioExistente.setEmail(usuarioDTO.email());
        usuarioExistente.setTelefone(usuarioDTO.telefone());

        // Lista de novos endereços fornecidos
        List<EnderecoDTO> novosEnderecosDTO = usuarioDTO.enderecos();

        // Lista de endereços existentes do usuário
        List<EnderecoEntity> enderecosExistente = usuarioExistente.getEnderecos();

        for (EnderecoDTO enderecoDTO : novosEnderecosDTO) {
            // Verifica se o endereço possui um ID, caso não possua, é um endereço novo
            if (enderecoDTO.id() != null) {
                // Se o ID do endereço estiver presente, encontra o endereço correspondente na lista existente
                for (EnderecoEntity enderecoExistente : enderecosExistente) {
                    // Se encontrar o endereço com o mesmo ID, atualiza os dados
                    if (enderecoExistente.getId().equals(enderecoDTO.id())) {
                        enderecoExistente.setCep(enderecoDTO.cep());
                        enderecoExistente.setLogradouro(enderecoDTO.logradouro());
                        enderecoExistente.setBairro(enderecoDTO.bairro());
                        enderecoExistente.setLocalidade(enderecoDTO.localidade());
                        enderecoExistente.setUf(enderecoDTO.uf());
                        break;
                    }
                }
            } else {
                // Se o ID estiver ausente,então é um novo endereço
                EnderecoEntity novoEndereco = new EnderecoEntity(
                        enderecoDTO.cep(),
                        enderecoDTO.logradouro(),
                        enderecoDTO.bairro(),
                        enderecoDTO.localidade(),
                        enderecoDTO.uf()
                );
                enderecosExistente.add(novoEndereco);
            }
        }

        // Salva as alterações e retorna o usuario atualizado
        return usuarioRepository.save(usuarioExistente);
    }

    public void deletarUsuario(Long id) {
        UsuarioEntity usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario não encontrado"));
        usuarioRepository.delete(usuarioExistente);
    }

    public List<UsuarioEntity> findAllUsuarios(UsuarioDTO usuarioDTO) {
        if (usuarioDTO.id() != null
                || !usuarioDTO.nome().isEmpty()
                || !usuarioDTO.email().isEmpty()
                || !usuarioDTO.telefone().isEmpty()
                || !usuarioDTO.cpf().isEmpty()) {
            return usuarioRepository.findByIdOrNomeOrEmailOrTelefoneOrCpf(
                    usuarioDTO.id(),
                    usuarioDTO.nome(),
                    usuarioDTO.email(),
                    usuarioDTO.telefone(),
                    usuarioDTO.cpf());
        } else {
            return usuarioRepository.findAll();
        }

    }
}