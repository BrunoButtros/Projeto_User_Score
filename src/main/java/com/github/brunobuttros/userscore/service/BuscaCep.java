package com.github.brunobuttros.userscore.service;

import com.github.brunobuttros.userscore.entity.EnderecoEntity;
import com.github.brunobuttros.userscore.exceptions.CepInvalidoException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class BuscaCep {
    @Value("${viacep.url}")
    private String viaCepUrl;

    public EnderecoEntity getEnderecoEntity(String cep) {
        RestTemplate restTemplate = new RestTemplate();
        String url = viaCepUrl + cep + "/json/";

        try {
            return restTemplate.getForObject(url, EnderecoEntity.class);

        } catch (RestClientException e) {
            throw new CepInvalidoException("CEP inválido ou não encontrado: " + cep);
        }
    }
}
