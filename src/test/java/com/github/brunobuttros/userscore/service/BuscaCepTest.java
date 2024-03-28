package com.github.brunobuttros.userscore.service;

import com.github.brunobuttros.userscore.entity.EnderecoEntity;
import com.github.brunobuttros.userscore.exceptions.CepInvalidoException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BuscaCepTest {
    @Test
    public void testGetEnderecoEntity() throws IOException {
        // Cenário
        String cep = "170014270";

        // Mock da classe "BuscaCep"
        BuscaCep buscaCepMock = mock(BuscaCep.class);
        when(buscaCepMock.getEnderecoEntity(cep)).thenReturn(new EnderecoEntity("170014270", "Logradouro",
                "Bairro", "Localidade", "UF"));

        // Execução do método que retorna EnderecoEntity
        EnderecoEntity enderecoEntity = buscaCepMock.getEnderecoEntity(cep);

        // Asserts
        assertNotNull(enderecoEntity);
        assertEquals("170014270", enderecoEntity.getCep());
        assertEquals("Logradouro", enderecoEntity.getLogradouro());
        assertEquals("Bairro", enderecoEntity.getBairro());
        assertEquals("Localidade", enderecoEntity.getLocalidade());
        assertEquals("UF", enderecoEntity.getUf());
    }
    @Test
    public void testGetEnderecoEntity_CepInvalido() {
        //Cenário
        String cepInvalido = "CEP_INVALIDO";

        //Mock da classe "BuscaCep"
        BuscaCep buscaCepMock = mock(BuscaCep.class);
        when(buscaCepMock.getEnderecoEntity(cepInvalido)).thenThrow(new CepInvalidoException("CEP inválido"));

        //Execução do método que retorna o EnderecoEntity com CEP inválido
        assertThrows(CepInvalidoException.class, () -> buscaCepMock.getEnderecoEntity(cepInvalido));
    }
}
