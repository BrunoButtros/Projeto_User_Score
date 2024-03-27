package com.github.brunobuttros.userscore.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class UsuarioEntityTest {
    @Test
    public void testParaGettersAndSetters() {
        UsuarioEntity usuario = new UsuarioEntity();
        //Definindo valores nos atributos usando os setters
        usuario.setId(1L);
        usuario.setNome("Jackson");
        usuario.setEmail("Jackson@exemplo.com");
        usuario.setTelefone("123456");
        usuario.setCpfOuRg("12345");
        usuario.setScore(100);

        //Testar os getters para verificar se os valores foram atribuidos corretamente
        assertEquals(1L, usuario.getId().longValue());
        assertEquals("Jackson", usuario.getNome());
        assertEquals("Jackson@exemplo.com", usuario.getEmail());
        assertEquals("123456", usuario.getTelefone());
        assertEquals("12345", usuario.getCpfOuRg());
        assertEquals(100, usuario.getScore());

    }
}