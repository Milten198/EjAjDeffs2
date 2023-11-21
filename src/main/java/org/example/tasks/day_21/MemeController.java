package org.example.tasks.day_21;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.day_21.RenderImageResponse;
import org.example.utils.BearerToken;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

public class MemeController {

    public static RenderImageResponse renderImage(String templateId, String text, String imageUrl) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper objectMapper = new ObjectMapper();
        final String url = "https://get.renderform.io/api/v2/render";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-API-KEY", BearerToken.RENDER_FORM_API_KEY);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("template", templateId);

        Map<String, String> data = new HashMap<>();
        data.put("TEXT.color", "#eeeeee");
        data.put("TEXT.text", text);
        data.put("IMAGE.src", imageUrl);
        requestBody.put("data", data);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        String response = restTemplate.postForObject(url, requestEntity, String.class);
        System.out.println(response);
        return objectMapper.readValue(response, RenderImageResponse.class);
    }
}
