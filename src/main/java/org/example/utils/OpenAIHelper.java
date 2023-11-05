package org.example.utils;

import org.example.dto.openai.chatCompletion.ChatCompletionMessage;
import org.example.dto.openai.chatCompletion.ChatCompletionRequest;
import org.example.dto.openai.chatCompletion.ChatCompletionResponse;
import org.example.dto.openai.embedding.EmbeddingRequest;
import org.example.dto.openai.embedding.EmbeddingResponse;
import org.example.dto.openai.moderation.ModerationResponse;
import org.example.dto.openai.whisper.WhisperResponse;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
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

    public static EmbeddingResponse embedding(String text, String model) {
        String url = "https://api.openai.com/v1/embeddings";
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("Authorization", "Bearer " + BearerToken.OPENAI_API_KEY);

        EmbeddingRequest embeddingRequest = new EmbeddingRequest(text, model);

        HttpEntity<EmbeddingRequest> entity = new HttpEntity<>(embeddingRequest, headers);

        ResponseEntity<EmbeddingResponse> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                entity,
                EmbeddingResponse.class
        );
        return response.getBody();
    }

    public static WhisperResponse whisper(String file, String model) throws IOException {
        String url = "https://api.openai.com/v1/audio/transcriptions";
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "multipart/form-data");
        headers.add("Authorization", "Bearer " + BearerToken.OPENAI_API_KEY);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("model", model);

        ClassPathResource resource = new ClassPathResource(file);

        FileSystemResource fileSystemResource = new FileSystemResource(resource.getFile());
        body.add("file", fileSystemResource);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<WhisperResponse> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, WhisperResponse.class);

        System.out.println(response.getBody());
        return response.getBody();
    }
}
