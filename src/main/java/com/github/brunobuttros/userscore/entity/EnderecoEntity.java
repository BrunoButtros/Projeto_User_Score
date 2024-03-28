package com.github.brunobuttros.userscore.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class EnderecoEntity {

        private String cep;
        private String bairro;
        private String localidade;
        private String uf;

        @ManyToOne
        private UsuarioEntity usuario;

        public EnderecoEntity(String cep, String bairro, String localidade, String uf) {
                this.cep = cep;
                this.bairro = bairro;
                this.localidade = localidade;
                this.uf = uf;
        }
        @Override
        public String toString() {
                return "EnderecoEntity{ cep = " + cep +
                        ", bairro = " + bairro +
                        ", localidade = " + localidade +
                        ", uf = " + uf +
                        ", usuario = " + usuario +
                        '}';
        }



}

