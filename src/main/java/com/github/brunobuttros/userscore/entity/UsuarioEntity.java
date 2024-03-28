package com.github.brunobuttros.userscore.entity;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UsuarioEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String email;
    private String telefone;
    private String cpfOuRg;
    private int score;

    @OneToMany (mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<EnderecoEntity> enderecos = new ArrayList<>(3);

}
