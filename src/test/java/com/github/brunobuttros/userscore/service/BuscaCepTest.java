package com.github.brunobuttros.userscore.service;

import com.github.brunobuttros.userscore.entity.EnderecoEntity;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

}
