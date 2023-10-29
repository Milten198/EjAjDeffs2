package org.example.utils;

import org.example.dto.openai.ChatCompletionMessage;
import org.example.dto.openai.ChatCompletionRequest;
import org.example.dto.openai.ChatCompletionResponse;
import org.example.dto.openai.ModerationResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

public class OpenAIHelper {

    static RestTemplate restTemplate = new RestTemplate();

    public static ModerationResponse moderate(String phrase) {
        String url = "https://api.openai.com/v1/moderations";
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("Authorization", "Bearer " + BearerToken.OPENAI_API_KEY);

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

    public static ChatCompletionResponse chatCompletion(String userMessage, String systemMessage, String model) {
        String url = "https://api.openai.com/v1/chat/completions";
        RestTemplate restTemplate = new RestTemplate();

        ChatCompletionRequest chatCompletionRequest = new ChatCompletionRequest();
        chatCompletionRequest.setModel(model);

        ChatCompletionMessage system = new ChatCompletionMessage("system", systemMessage);
        ChatCompletionMessage user = new ChatCompletionMessage("user", userMessage);
        List<ChatCompletionMessage> chatCompletionMessageList = new ArrayList<>();
        chatCompletionMessageList.add(system);
        chatCompletionMessageList.add(user);
        chatCompletionRequest.setMessages(chatCompletionMessageList);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("Authorization", "Bearer " + BearerToken.OPENAI_API_KEY);

        HttpEntity<ChatCompletionRequest> entity = new HttpEntity<>(chatCompletionRequest, headers);

        ResponseEntity<ChatCompletionResponse> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                entity,
                ChatCompletionResponse.class
        );
        return response.getBody();
    }
}
