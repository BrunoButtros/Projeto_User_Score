package com.github.brunobuttros.userscore.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Table(name = "users")
@Entity(name = "UsersJPA")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class    UsuarioEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String login;
    private String password;
    private String nome;
    private String email;
    private String telefone;
    private String cpf;
    private int score;
    private String cep;
    private UsuarioRole role;

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL)
    private EnderecoEntity endereco;


    public UsuarioEntity(String login,String password, UsuarioRole role){
        this.login = login;
        this.password = password;
        this.role = role;
    }




   /* public UsuarioEntity(String nome, String email, String telefone, String cpf, int score, String cep) {
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.cpf = cpf;
        this.score = score;
        this.cep = cep;
    }

    */

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.role == UsuarioRole.ADMIN)return
                List.of(new SimpleGrantedAuthority("Role_ADMIN"),
                new SimpleGrantedAuthority("Role_USER"));
        else return List.of(new SimpleGrantedAuthority("Role_USER"));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
