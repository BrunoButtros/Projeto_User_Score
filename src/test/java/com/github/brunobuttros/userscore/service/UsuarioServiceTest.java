package com.github.brunobuttros.userscore.service;

import com.github.brunobuttros.userscore.dto.EnderecoDTO;
import com.github.brunobuttros.userscore.dto.UsuarioDTO;
import com.github.brunobuttros.userscore.entity.EnderecoEntity;
import com.github.brunobuttros.userscore.repository.UsuarioRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

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
    @Test(expected = RuntimeException.class)
    public void testCadastrarUsuario_ErroBuscaCep() {
        List<EnderecoDTO> enderecos = new ArrayList<>();
        enderecos.add(new EnderecoDTO(1l, "CEP1", "LOGRADOURO1", "BAIRRO1", "LOCALIDADE1", "UF1"));

        UsuarioDTO usuarioDTO = new UsuarioDTO
                (1l, "Nome1", "Nome@exemplo.com", "123456", "12345678", 100, enderecos);

        when(buscaCep.getEnderecoEntity(anyString())).thenThrow(new RuntimeException("Erro ao buscar endereço"));

        usuarioService.cadastrarUsuario(usuarioDTO);
    }
}

