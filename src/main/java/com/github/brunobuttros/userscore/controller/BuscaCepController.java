package com.github.brunobuttros.userscore.controller;

import com.github.brunobuttros.userscore.dto.EnderecoDTO;
import com.github.brunobuttros.userscore.entity.EnderecoEntity;
import com.github.brunobuttros.userscore.service.BuscaCep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/buscaCep")
public class BuscaCepController {
    private final BuscaCep buscaCep;

    @Autowired
    public BuscaCepController(BuscaCep buscaCep){
        this.buscaCep = buscaCep;
    }

    @GetMapping("/{cep}")
    public EnderecoDTO getEnderecoByCep(@PathVariable String cep) {
        EnderecoEntity enderecoEntity = buscaCep.getEnderecoEntity(cep);
        return new EnderecoDTO(enderecoEntity.getId(),
                enderecoEntity.getCep(),
                enderecoEntity.getBairro(),
                enderecoEntity.getLocalidade(),
                enderecoEntity.getUf());
    }

}
