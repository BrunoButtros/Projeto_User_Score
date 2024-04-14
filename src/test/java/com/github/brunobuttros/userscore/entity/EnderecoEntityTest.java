package com.github.brunobuttros.userscore.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EnderecoEntityTest {
    @Test
    public void testConstrutor() {
        //cenário
        String cep = "17014270";
        String logradouro = "Rua";
        String bairro = "Bairro";
        String localidade = "localidade";
        String uf = "UF";


        //ação
        EnderecoEntity endereco = new EnderecoEntity(cep, logradouro, bairro, localidade, uf);

        //assert
        Assertions.assertEquals(cep, endereco.getCep());
        Assertions.assertEquals(logradouro, endereco.getLogradouro());
        Assertions.assertEquals(bairro, endereco.getBairro());
        Assertions.assertEquals(localidade, endereco.getLocalidade());
        Assertions.assertEquals(uf, endereco.getUf());
    }


    /*@Test(expected = IllegalArgumentException.class)
    public void testValidacaoCep() {
        new EnderecoEntity("1234567", "Rua", "Bairro", "Cidade", "UF");
    }

     */
}
