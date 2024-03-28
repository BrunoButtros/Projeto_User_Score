package com.github.brunobuttros.userscore.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Table(name = "Adress")
@Entity(name = "AdressJPA")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoEntity {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

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

