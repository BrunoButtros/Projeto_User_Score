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
        private String logradouro;
        private String bairro;
        private String localidade;
        private String uf;

        @ManyToOne
        private UsuarioEntity usuario;

     public EnderecoEntity(String cep, String logradouro, String bairro, String localidade, String uf) {
                this.cep = cep;
                this.logradouro = logradouro;
                this.bairro = bairro;
                this.localidade = localidade;
                this.uf = uf;
        }


        @Override
        public String toString() {
                return "EnderecoEntity{ cep = " + cep +
                        ", logradouro = " + logradouro +
                        ", bairro = " + bairro +
                        ", localidade = " + localidade +
                        ", uf = " + uf +
                        '}';
        }



}

