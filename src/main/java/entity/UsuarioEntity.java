package entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Usuario {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String nome;
    private String endereco;
    private String enderecoExtra;
    private String telefone;
    private String cpfOuRg;
    private int score;
}
