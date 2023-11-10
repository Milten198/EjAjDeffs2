package org.example.tasks.day_14;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.common.Token;
import org.example.dto.day_14.SearchEmbedding;
import org.example.dto.day_14.SearchQueryResponse.ResponseForSearchQuery;
import org.example.dto.day_14.SearchQuestion;
import org.example.dto.day_14.SearchTask;
import org.example.dto.openai.embedding.EmbeddingResponse;
import org.example.utils.OpenAIHelper;
import org.example.utils.TaskHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.example.utils.QdrantHelper.*;

public class Search {

    public static void solveSearchApiTask() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Token token = TaskHelper.getToken("search");
        String task = TaskHelper.getTask(token.getToken());
        SearchQuestion searchQuestion = objectMapper.readValue(task, SearchQuestion.class);
        EmbeddingResponse embedding = OpenAIHelper.embedding(searchQuestion.getQuestion(), "text-embedding-ada-002");
        String answer = searchForPosition("Search", embedding.getData().get(0).getEmbedding());
        ResponseForSearchQuery responseForSearchQuery = objectMapper.readValue(answer, ResponseForSearchQuery.class);
        TaskHelper.postAnswer(responseForSearchQuery.getResult().get(0).getPayload().getUrl(), token.getToken());
    }

    private static void feedDB(String collectionName) throws JsonProcessingException {
        List<SearchEmbedding> allEmbeddings = prepareDataForQdrant();
        pushDataToCollection(collectionName, allEmbeddings);
    }
    public static List<SearchEmbedding> prepareDataForQdrant() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String textFromTheUrl = TaskHelper.getTextFromTheUrl("https://unknow.news/archiwum.json");
        List<SearchTask> positions = Arrays.asList(objectMapper.readValue(textFromTheUrl, SearchTask[].class));

        List<SearchEmbedding> allEmbeddings = new ArrayList<>();
        for (int i = 0; i < 300; i++) {
            SearchTask singlePosition = positions.get(i);
            List<Double> embedding = OpenAIHelper.embedding(singlePosition.getInfo(), "text-embedding-ada-002")
                    .getData().get(0).getEmbedding();
            SearchEmbedding searchEmbedding = new SearchEmbedding(UUID.randomUUID(), embedding, singlePosition);
            allEmbeddings.add(searchEmbedding);
        }
        return allEmbeddings;
    }
}

