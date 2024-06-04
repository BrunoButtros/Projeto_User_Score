package com.github.brunobuttros.userscore.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.brunobuttros.userscore.entity.EnderecoEntity;
import com.github.brunobuttros.userscore.exceptions.CepInvalidoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Service
public class BuscaCepClient {
    private static final Logger logger = LoggerFactory.getLogger(BuscaCepClient.class);


    @Value("${viacep.url}")
    private String viaCepUrl;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public BuscaCepClient(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public EnderecoEntity buscarEnderecoPorCep(String cep) {
        logger.info("Buscando endereço para o CEP: {}", cep);
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