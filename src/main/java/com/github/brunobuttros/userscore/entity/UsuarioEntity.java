package com.github.brunobuttros.userscore.entity;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "users")
@Entity(name = "UsersJPA")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class UsuarioEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String email;
    private String telefone;
    private String cpf;
    private int score;
    private String cep;

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL)
    private EnderecoEntity endereco;

    public UsuarioEntity(String nome, String email, String telefone, String cpf, int score, String cep) {
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.cpf = cpf;
        this.score = score;
        this.cep = cep;
    }
}
