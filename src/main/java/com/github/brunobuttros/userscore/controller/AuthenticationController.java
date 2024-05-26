package com.github.brunobuttros.userscore.controller;

import com.github.brunobuttros.userscore.dto.AuthenticationDTO;
import com.github.brunobuttros.userscore.dto.LoginResponseDTO;
import com.github.brunobuttros.userscore.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

        @PostMapping("/login")
        public ResponseEntity<LoginResponseDTO> login(@RequestBody @Validated AuthenticationDTO data) {
            LoginResponseDTO response = authenticationService.authenticate(data);
            return ResponseEntity.ok(response);
        }
}