package com.github.brunobuttros.userscore.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.brunobuttros.userscore.entity.EnderecoEntity;
import com.github.brunobuttros.userscore.exceptions.CepInvalidoException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Service
public class BuscaCep {

    @Value("${viacep.url}")
    private String viaCepUrl;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public BuscaCep(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public EnderecoEntity buscarEnderecoPorCep(String cep) {
        return buscarEndereco(cep);
    }

    private EnderecoEntity buscarEndereco(String cep) {
        String url = viaCepUrl + cep + "/json/";

        try {
            String json = restTemplate.getForObject(url, String.class);
            return objectMapper.readValue(json, EnderecoEntity.class);
        } catch (RestClientException | IOException e) {
            throw new CepInvalidoException("CEP inválido ou não encontrado: " + cep);
        }
    }
}
