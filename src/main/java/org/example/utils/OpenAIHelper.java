package org.example.utils;

import org.example.dto.openai.chatCompletion.ChatCompletionMessage;
import org.example.dto.openai.chatCompletion.ChatCompletionRequest;
import org.example.dto.openai.chatCompletion.ChatCompletionResponse;
import org.example.dto.openai.embedding.EmbeddingRequest;
import org.example.dto.openai.embedding.EmbeddingResponse;
import org.example.dto.openai.moderation.ModerationResponse;
import org.example.dto.openai.vision.request.Content;
import org.example.dto.openai.vision.request.ImageUrl;
import org.example.dto.openai.vision.request.Message;
import org.example.dto.openai.vision.request.VisionRequest;
import org.example.dto.openai.vision.response.VisionResponse;
import org.example.dto.openai.whisper.WhisperResponse;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.*;

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

    public static VisionResponse vision(String text, String urlToImage) {
        final String url = "https://api.openai.com/v1/chat/completions";
        final String apiKey = BearerToken.OPENAI_API_KEY; // Replace with your OpenAI API key

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + apiKey);

        Map<String, Object> body = new HashMap<>();
        body.put("model", "gpt-4-vision-preview");

        List<Map<String, Object>> messages = new ArrayList<>();
        Map<String, Object> userMessage = new HashMap<>();
        userMessage.put("role", "user");

        List<Map<String, Object>> content = new ArrayList<>();

        Map<String, Object> textContent = new HashMap<>();
        textContent.put("type", "text");
        textContent.put("text", text);
        content.add(textContent);

        Map<String, Object> imageUrlContent = new HashMap<>();
        imageUrlContent.put("type", "image_url");

        Map<String, String> imageUrl = new HashMap<>();
        imageUrl.put("url", urlToImage);
        imageUrlContent.put("image_url", imageUrl);

        content.add(imageUrlContent);

        userMessage.put("content", content);
        messages.add(userMessage);

        body.put("messages", messages);
        body.put("max_tokens", 300);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        ResponseEntity<VisionResponse> response = restTemplate.postForEntity(url, entity, VisionResponse.class);

        return response.getBody();
    }

//    TODO use this method to create request for vision()
    private static VisionRequest createVisionRequest(String text, String urlToImage) {
        VisionRequest visionRequest = new VisionRequest();
        visionRequest.setMaxTokens(300);
        visionRequest.setModel("gpt-4-vision-preview");

        Message message = new Message();
        message.setRole("user");
        Content contentText = new Content();
        contentText.setType("text");
        contentText.setText(text);

        Content contentImage = new Content();
        contentImage.setType("image_url");
        ImageUrl imageUrl = new ImageUrl();
        imageUrl.setUrl(urlToImage);
        contentImage.setImage_url(imageUrl);
        message.setContent(Arrays.asList(contentText, contentImage));

        visionRequest.setMessages(List.of(message));
        return visionRequest;
    }
}
