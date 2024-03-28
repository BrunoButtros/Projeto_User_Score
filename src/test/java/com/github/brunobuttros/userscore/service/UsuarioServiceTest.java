package com.github.brunobuttros.userscore.service;

import com.github.brunobuttros.userscore.dto.EnderecoDTO;
import com.github.brunobuttros.userscore.dto.UsuarioDTO;
import com.github.brunobuttros.userscore.entity.EnderecoEntity;
import com.github.brunobuttros.userscore.entity.UsuarioEntity;
import com.github.brunobuttros.userscore.repository.UsuarioRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private BuscaCep buscaCep;

    @InjectMocks
    private UsuarioService usuarioService;


    @Test
    public void testCadastrarUsuario_Sucesso() {
        List<EnderecoDTO> enderecos = new ArrayList<>();
        enderecos.add(new EnderecoDTO
                (1l, "CEP1", "LOGRADOURO1", "BAIRRO1", "LOCALIDADE1", "UF1"));

        enderecos.add(new EnderecoDTO
                (2l, "CEP2", "LOGRADOURO2", "BAIRRO2", "LOCALIDADE2", "UF2"));


        UsuarioDTO usuarioDTO = new UsuarioDTO
                (1l, "Nome1", "Nome@exemplo.com", "123456", "12345678", 100, enderecos
                );

        EnderecoEntity enderecoEntity1 = new EnderecoEntity
                ("CEP1", "LOGRADOURO1", "BAIRRO1", "LOCALIDADE1", "UF1");

        EnderecoEntity enderecoEntity2 = new EnderecoEntity
                ("CEP2", "LOGRADOURO2", "BAIRRO2", "LOCALIDADE2", "UF2");

        when(buscaCep.getEnderecoEntity("CEP1")).thenReturn(enderecoEntity1);
        when(buscaCep.getEnderecoEntity("CEP2")).thenReturn(enderecoEntity2);

        usuarioService.cadastrarUsuario(usuarioDTO);

        verify(usuarioRepository, times(1)).save(any());
    }
    @Test
    public void testAtualizarCadastro_Sucesso() {
        // Simula os dados do usuário e seus endereços
        List<EnderecoDTO> enderecos = new ArrayList<>();
        enderecos.add(new EnderecoDTO(1L, "CEP1", "LOGRADOURO1", "BAIRRO1", "LOCALIDADE1", "UF1"));
        enderecos.add(new EnderecoDTO(2L, "CEP2", "LOGRADOURO2", "BAIRRO2", "LOCALIDADE2", "UF2"));

        UsuarioDTO usuarioDTO = new UsuarioDTO(1L, "Nome1", "Nome@exemplo.com", "123456", "12345678", 100, enderecos);

        // Simula os objetos EnderecoEntity retornados pela busca de CEP
        EnderecoEntity enderecoEntity1 = new EnderecoEntity("CEP1", "LOGRADOURO1", "BAIRRO1", "LOCALIDADE1", "UF1");
        EnderecoEntity enderecoEntity2 = new EnderecoEntity("CEP2", "LOGRADOURO2", "BAIRRO2", "LOCALIDADE2", "UF2");


        // Simula o usuário existente no banco de dados
        UsuarioEntity usuarioExistente = new UsuarioEntity();
        usuarioExistente.setId(1L);
        usuarioExistente.setEmail("email@antigo.com");
        usuarioExistente.setTelefone("1234567890");
        usuarioExistente.setEnderecos(new ArrayList<>());

        // Mock de usuarioRepository para retornar o usuário existente
        when(usuarioRepository.findById(1L)).thenReturn(java.util.Optional.of(usuarioExistente));

        // Chama o método a ser testado
        usuarioService.atualizarUsuario(1L, usuarioDTO);

        // Verifica se usuarioExistente foi salvo no usuarioRepository
        verify(usuarioRepository, times(1)).save(usuarioExistente);

        // Verifica se os dados do usuário foram atualizados corretamente
        assertEquals(usuarioDTO.email(), usuarioExistente.getEmail());
        assertEquals(usuarioDTO.telefone(), usuarioExistente.getTelefone());
    }

    @Test(expected = RuntimeException.class)
    public void testAtualizarCadastro_ErroSalvarUsuario() {
        // Simula os dados do usuário e seus endereços
        List<EnderecoDTO> enderecos = new ArrayList<>();
        enderecos.add(new EnderecoDTO(1L, "CEP1", "LOGRADOURO1", "BAIRRO1", "LOCALIDADE1", "UF1"));

        UsuarioDTO usuarioDTO = new UsuarioDTO(1L, "Nome1", "Nome@exemplo.com", "123456", "12345678", 100, enderecos);

        // Simula o usuário existente no banco de dados
        UsuarioEntity usuarioExistente = new UsuarioEntity();
        usuarioExistente.setId(1L);
        usuarioExistente.setEmail("email@antigo.com");
        usuarioExistente.setTelefone("1234567890");
        usuarioExistente.setEnderecos(new ArrayList<>());

        when(usuarioRepository.findById(1L)).thenReturn(java.util.Optional.of(usuarioExistente));

        // Exceção ao salvar usuario
        doThrow(new RuntimeException("Erro ao salvar usuário")).when(usuarioRepository).save(any());

        usuarioService.atualizarUsuario(1L, usuarioDTO);
    }


}


