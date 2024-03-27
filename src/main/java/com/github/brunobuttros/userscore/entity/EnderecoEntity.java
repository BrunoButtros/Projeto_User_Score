package com.github.brunobuttros.userscore.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoEntity {
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String rua;
        private String cidade;
        private String estado;
        private String cep;
        private String numero;

        @ManyToOne
        private UsuarioEntity usuario;
    }

