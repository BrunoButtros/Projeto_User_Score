package com.github.brunobuttros.userscore.repository;

import com.github.brunobuttros.userscore.entity.UsuarioEntity;
import com.github.brunobuttros.userscore.entity.UsuarioRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {
    List<UsuarioEntity> findByNome(String nome);

    List<UsuarioEntity> findByEmail(String email);

    List<UsuarioEntity> findByTelefone(String telefone);

    List<UsuarioEntity> findByCpf(String cpf);

    UserDetails findByLogin(String login);

    boolean existsByLogin(String login);

    boolean existsByCpf(String cpf);

    default List<UsuarioEntity> findByRole(String role) {
        return findByRole(UsuarioRole.valueOf(role.toUpperCase()));
    }

    List<UsuarioEntity> findByRole(UsuarioRole role);
}