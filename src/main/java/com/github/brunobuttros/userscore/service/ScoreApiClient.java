package com.github.brunobuttros.userscore.service;

import com.github.brunobuttros.userscore.exceptions.ScoreApiException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ScoreApiClient {

    private final RestTemplate restTemplate;

    @Value("${score.api.url}")
    private String scoreApiUrl;

    public ScoreApiClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public int getScore(Long userId) {
        String apiUrl = scoreApiUrl + "/usuarios/" + userId + "/score";
        ResponseEntity<Integer> response = restTemplate.getForEntity(apiUrl, Integer.class);
        Integer score = response.getBody();
        if (score != null) {
            return score;
        } else {
            throw new ScoreApiException("O corpo da resposta da API de score está vazio ou nulo");
        }
    }
}