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
}
