package com.github.brunobuttros.userscore.entity;

import entity.UsuarioEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class UsuarioEntityTest {
    @Test
    public void testParaGettersAndSetters() {
        UsuarioEntity usuario = new UsuarioEntity();

        usuario.setId(1L);
        usuario.setNome("Jackson");
        usuario.setEmail("Jackson@exemplo.com");
        usuario.setTelefone("123456");
        usuario.setCpfOuRg("12345");
        usuario.setScore(100);


        assertEquals(1L, usuario.getId().longValue());
        assertEquals("João", usuario.getNome());
        assertEquals("joao@example.com", usuario.getEmail());
        assertEquals("123456789", usuario.getTelefone());
        assertEquals("123456789", usuario.getCpfOuRg());
        assertEquals(100, usuario.getScore());

    }
}