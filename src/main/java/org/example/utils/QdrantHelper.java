package org.example.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.day_14.Points;
import org.example.dto.day_14.SearchEmbedding;
import org.example.dto.day_14.SearchQuery;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class QdrantHelper {

    public static void createCollection(String collectionName) {
        final String baseUrl = "http://localhost:6333/collections/{collection_name}";
        RestTemplate restTemplate = new RestTemplate();

        // Define the collection configuration
        String collectionConfig = "{"
                + "\"vectors\": {"
                + "\"size\": 1536," // Vector size (dimensions)
                + "\"distance\": \"Cosine\"," // Distance function
                + "\"on_disk\": true" // Store vectors on disk
                + "}"
                + "}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Prepare the HTTP PUT request
        HttpEntity<String> request = new HttpEntity<>(collectionConfig, headers);

        restTemplate.put(baseUrl, request, collectionName);

    }

    //TODO please make it generic
    public static void pushDataToCollection(String collectionName, List<SearchEmbedding> embeddingList) throws JsonProcessingException {
        final String baseUrl = String.format("http://localhost:6333/collections/%s/points", collectionName);
        RestTemplate restTemplate = new RestTemplate();

        Points points = new Points();
        points.setPoints(embeddingList);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonData = objectMapper.writeValueAsString(points);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Wrap the data in an HttpEntity
        HttpEntity<String> request = new HttpEntity<>(jsonData, headers);

        ResponseEntity<String> response = restTemplate.exchange(baseUrl, HttpMethod.PUT, request, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            System.out.println("Data inserted successfully!");
        } else {
            System.out.println("Failed to insert data. Status code: " + response.getStatusCode());
        }
    }

    public static String searchForPosition(String collectionName, List<Double> vector) {
        String searchUrl = String.format("http://localhost:6333/collections/%s/points/search", collectionName);
        RestTemplate restTemplate = new RestTemplate();

        SearchQuery searchQuery = new SearchQuery();
        searchQuery.setLimit(1);
        searchQuery.setVector(vector);
        searchQuery.setWith_payload(true);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<SearchQuery> entity = new HttpEntity<>(searchQuery, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(searchUrl, entity, String.class);

        return response.getBody();
    }

}
