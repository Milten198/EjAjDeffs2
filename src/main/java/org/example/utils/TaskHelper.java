package org.example.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import org.example.dto.common.Token;
import org.example.dto.common.ApiKeyPayload;
import org.example.dto.common.AnswerPayload;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.function.Supplier;

public class TaskHelper {

    static RestTemplate restTemplate = new RestTemplate();

    public static String postQuestion(String question, String token) {
        String url = "https://zadania.aidevs.pl/task/" + token;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED);

        // Set the form data
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("question", question);

        // Create an HttpEntity object with the headers and form data
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                entity,
                String.class
        );

        System.out.println(response.getBody());
        return response.getBody();
    }


    public static String postAnswer(Object answer, String token) {
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

    public static String getTextFromTheUrl(String url) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:109.0) Gecko/20100101 Firefox/110.0");
        headers.add("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        headers.add("Accept-Language", "en-US,en;q=0.5");
        headers.add("DNT", "1"); // Do Not Track
        headers.add("Connection", "keep-alive");
        headers.add("Upgrade-Insecure-Requests", "1");
        headers.add("Pragma", "no-cache");

        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);


        Supplier<ResponseEntity<String>> decoratedSupplier = Retry.decorateSupplier(retryConfig(), () -> restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                String.class));

        ResponseEntity<String> response = decoratedSupplier.get();

        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            throw new RuntimeException("Failed to fetch data: " + response.getStatusCode());
        }
    }

    public static Retry retryConfig() {
        RetryConfig config = RetryConfig.custom()
                .maxAttempts(3)
                .waitDuration(Duration.ofSeconds(10))
                .build();

        RetryRegistry registry = RetryRegistry.of(config);
        return registry.retry("id", config);
    }


}
