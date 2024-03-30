package com.github.brunobuttros.userscore.controller;


import com.github.brunobuttros.userscore.dto.UsuarioDTO;
import com.github.brunobuttros.userscore.entity.UsuarioEntity;
import com.github.brunobuttros.userscore.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping  //O método cadastrar, recebe o usuarioDTO no corpo da solicitação, e chama o método da usuario Service
    public ResponseEntity<UsuarioEntity> cadastrarUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        UsuarioEntity usuarioCadastrado = usuarioService.cadastrarUsuario(usuarioDTO);
        // Retorna o status HTTP 201 (CREATED) e retorna o usuario recém criado
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCadastrado);
    }

    @PutMapping("/{id}")
    // O método atualizar, recebe o ID a ser atualizado e o objeto usuarioDTO, e chama o método correspondente da service
    public ResponseEntity<UsuarioEntity> atualizarUsuario(@PathVariable Long id, @RequestBody UsuarioDTO usuarioDTO) {
        UsuarioEntity usuarioAtualizado = usuarioService.atualizarUsuario(id, usuarioDTO);
        //Retorna o status HTTP 2000 (OK)
        return new ResponseEntity<>(usuarioAtualizado, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) //Retorna HTTP 204
    public ResponseEntity<Void>deletarUsuario(@PathVariable Long id){
        //Recebe o id do usuário e chama o método correspondente
        usuarioService.deletarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<UsuarioEntity>> findAllUsuarios(UsuarioDTO usuarioDTO) {
        //Retorna os usuarios cadastrados
        List<UsuarioEntity> usuarios = usuarioService.findAllUsuarios(usuarioDTO);
        return ResponseEntity.ok().body(usuarios);
    }
}

