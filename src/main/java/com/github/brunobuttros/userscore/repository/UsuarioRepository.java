package com.github.brunobuttros.userscore.repository;

import com.github.brunobuttros.userscore.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {
    List<UsuarioEntity> findByIdOrNomeOrEmailOrTelefoneOrCpf(Long id, String nome, String email, String telefone, String cpf);

}