package org.example.tasks.day_12;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import org.example.dto.common.Token;
import org.example.dto.day_12.ScraperTask;
import org.example.dto.openai.chatCompletion.ChatCompletionResponse;
import org.example.utils.OpenAIHelper;
import org.example.utils.TaskHelper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.Duration;
import java.util.function.Supplier;

public class Scraper {

    public static void solveScraperApiTask() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Token token = TaskHelper.getToken("scraper");
        String task = TaskHelper.getTask(token.getToken());

        ScraperTask scraperTask = objectMapper.readValue(task, ScraperTask.class);
        String text = getTextFromTheUrl(scraperTask.getInput());
        String systemMessage = scraperTask.getQuestion() + "\n Odpowiedz po polsku w maksymalnie 200 znakach.";
        ChatCompletionResponse chatCompletionResponse = OpenAIHelper.chatCompletion(text, systemMessage, "gpt-4-1106-preview");
        String answer = TaskHelper.postAnswer(chatCompletionResponse.getChoices().get(0).getMessage().getContent(), token.getToken());
        System.out.println(answer);
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
