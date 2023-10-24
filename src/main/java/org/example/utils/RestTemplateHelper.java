package org.example.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.dto.day_1.Token;
import org.example.dto.day_1.payloads.ApiKeyPayload;
import org.example.dto.day_1.payloads.AnswerPayload;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

public class RestTemplateHelper {

    static RestTemplate restTemplate = new RestTemplate();

    public static String postAnswer(String answer, String token) {
        String url = "https://zadania.aidevs.pl/answer/" + token;

        // Create payload
        AnswerPayload payload = new AnswerPayload(answer);

        // Set headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create an entity with the object and headers
        HttpEntity<AnswerPayload> entity = new HttpEntity<>(payload, headers);

        // POST the Java object
        return restTemplate.postForObject(url, entity, String.class);
    }
    public static Token getToken(String taskName) throws JsonProcessingException {
        String url = "https://zadania.aidevs.pl/token/" + taskName;

        // Create payload
        ApiKeyPayload payload = new ApiKeyPayload("64db4c0e-47a8-4f31-bab1-75d16e7b4831");

        // Set headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create an entity with the object and headers
        HttpEntity<ApiKeyPayload> entity = new HttpEntity<>(payload, headers);

        // POST the Java object and return
        String authResponse = restTemplate.postForObject(url, entity, String.class);
        return ObjectMappers.deserializeToken(authResponse);
    }

    public static String getTask(String token) {
        String url = "https://zadania.aidevs.pl/task/" + token;

        // GET the task for the given token
        return restTemplate.getForObject(url, String.class);
    }
}
