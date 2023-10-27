package org.example.utils;

import org.example.dto.day_4.ModerationResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class OpenAPIHelper {

    static RestTemplate restTemplate = new RestTemplate();
    private static final String OPENAI_API_KEY = "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";

    public static ModerationResponse moderate(String phrase) {
        String url = "https://api.openai.com/v1/moderations";
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("Authorization", "Bearer " + OPENAI_API_KEY);

        String requestBody = "{\"input\": \"" + phrase + "\"}";

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<ModerationResponse> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                entity,
                ModerationResponse.class
        );

        return response.getBody();
    }
}
