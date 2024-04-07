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

    @PostMapping("/cadastrar")
    public ResponseEntity<UsuarioDTO> cadastrarUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        UsuarioEntity usuarioCadastrado = usuarioService.cadastrarUsuario(usuarioDTO);
        UsuarioDTO usuarioCadastradoDTO = usuarioService.convertEntityToDTO(usuarioCadastrado);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCadastradoDTO);
    }


    @PutMapping("{id}")
    public ResponseEntity<UsuarioDTO> atualizarCadastro(@PathVariable Long id,@RequestBody UsuarioDTO usuarioDTO){
        UsuarioEntity usuarioAtualizado = usuarioService.atualizarUsuario(id, usuarioDTO);
        UsuarioDTO usuarioAtualizadoDTO = usuarioService.convertEntityToDTO(usuarioAtualizado);
        return ResponseEntity.ok(usuarioAtualizadoDTO);
    }


    @GetMapping("/buscar")
    public ResponseEntity<List<UsuarioEntity>> buscarUsuarios(@RequestParam(required = false) Long id,
                                                              @RequestParam(required = false) String nome,
                                                              @RequestParam(required = false) String email,
                                                              @RequestParam(required = false) String telefone,
                                                              @RequestParam(required = false) String cpf) {
        List<UsuarioEntity> usuarios = usuarioService.buscarUsuarios(id, nome, email, telefone, cpf);
        return ResponseEntity.ok().body(usuarios);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<UsuarioDTO> deletarUsuario(@PathVariable Long id){
        usuarioService.deletarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}