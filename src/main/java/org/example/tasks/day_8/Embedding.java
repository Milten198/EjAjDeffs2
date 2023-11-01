package org.example.tasks.day_8;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.dto.common.Token;
import org.example.dto.openai.embedding.EmbeddingResponse;
import org.example.utils.OpenAIHelper;
import org.example.utils.TaskHelper;

public class Embedding {

    public static void solveEmbeddingApiTask() throws JsonProcessingException {
        Token token = TaskHelper.getToken("embedding");
        EmbeddingResponse embeddingResponse = OpenAIHelper.embedding("Hawaiian pizza", "text-embedding-ada-002");
        String answer = TaskHelper.postAnswer(embeddingResponse.getData().get(0).getEmbedding(), token.getToken());
        System.out.println(answer);
    }
}
