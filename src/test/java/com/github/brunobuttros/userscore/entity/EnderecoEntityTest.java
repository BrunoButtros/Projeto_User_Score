package com.github.brunobuttros.userscore.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EnderecoEntityTest {
    @Test
    public void testParaGettersAndSetters(){
        //Definindo valores nos atributos usando os setters

        EnderecoEntity endereco = new EnderecoEntity();
        endereco.setId(1L);
        endereco.setCep("170014270");
        endereco.setLogradouro("Rua Exemplo");
        endereco.setBairro("Bairro Exemplo");
        endereco.setLocalidade("Cidade Exemplo");
        endereco.setUf("UF");


        //Testando os getters para verificar se os valores foram atribuidos corretamente
        assertEquals(1L, endereco.getId().longValue());
        assertEquals("170014270", endereco.getCep());
        assertEquals("Rua Exemplo", endereco.getLogradouro());
        assertEquals("Bairro Exemplo", endereco.getBairro());
        assertEquals("Cidade Exemplo", endereco.getLocalidade());
        assertEquals("UF", endereco.getUf());

    }

}
