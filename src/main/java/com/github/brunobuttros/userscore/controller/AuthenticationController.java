package com.github.brunobuttros.userscore.controller;

import com.github.brunobuttros.userscore.dto.AuthenticationDTO;
import com.github.brunobuttros.userscore.dto.LoginResponseDTO;
import com.github.brunobuttros.userscore.entity.UsuarioEntity;
import com.github.brunobuttros.userscore.repository.UsuarioRepository;
import com.github.brunobuttros.userscore.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationMager;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private TokenService tokenService;
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Validated AuthenticationDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = this.authenticationMager.authenticate(usernamePassword);
        var token = tokenService.generateToken((UsuarioEntity) auth.getPrincipal());
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }
}
