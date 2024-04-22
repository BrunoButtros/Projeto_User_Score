package com.github.brunobuttros.userscore.controller;

import com.github.brunobuttros.userscore.dto.RegisterDTO;
import com.github.brunobuttros.userscore.dto.UserScoreDTO;
import com.github.brunobuttros.userscore.dto.UsuarioDTO;
import com.github.brunobuttros.userscore.entity.UsuarioEntity;
import com.github.brunobuttros.userscore.repository.UsuarioRepository;
import com.github.brunobuttros.userscore.service.BuscaCepClient;
import com.github.brunobuttros.userscore.service.ScoreApiClient;
import com.github.brunobuttros.userscore.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UsuarioController {
    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private ScoreApiClient scoreApiClient;
    @Autowired
    private BuscaCepClient buscaCepClient;

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Validated RegisterDTO data) {
        return usuarioService.register(data);
    }


    @PutMapping("{id}")
    public ResponseEntity<UsuarioDTO> atualizarCadastro(@PathVariable Long id, @RequestBody UsuarioDTO usuarioDTO) {
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
    public ResponseEntity<UsuarioDTO> deletarUsuario(@PathVariable Long id) {
        usuarioService.deletarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/score")
    public ResponseEntity<UserScoreDTO> getUserScoreById(@PathVariable Long id) {
        UserScoreDTO userScoreDTO = usuarioService.getUserScoreById(id);
        return ResponseEntity.ok(userScoreDTO);
    }
}