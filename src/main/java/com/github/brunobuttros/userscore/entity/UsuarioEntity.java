package com.github.brunobuttros.userscore.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Table(name = "users")
@Entity(name = "UsersJPA")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")

public class UsuarioEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String email;
    private String telefone;
    private String cpf;
    private int score;

    @OneToMany (mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<EnderecoEntity> enderecos = new ArrayList<>(3);

}
