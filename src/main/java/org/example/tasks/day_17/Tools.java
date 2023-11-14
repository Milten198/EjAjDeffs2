package org.example.tasks.day_17;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.common.Token;
import org.example.dto.day_17.ToolsTask;
import org.example.dto.openai.chatCompletion.ChatCompletionResponse;
import org.example.utils.OpenAIHelper;
import org.example.utils.TaskHelper;

import java.io.IOException;

public class Tools {

    public static void solveToolsApiTask() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Token token = TaskHelper.getToken("tools");
        String task = TaskHelper.getTask(token.getToken());
        System.out.println(task);
        ToolsTask toolsTask = objectMapper.readValue(task, ToolsTask.class);

        ChatCompletionResponse chatCompletionResponse = OpenAIHelper.chatCompletion(toolsTask.getQuestion(), systemMessage, "gpt-4-1106-preview");
        String answer = chatCompletionResponse.getChoices().get(0).getMessage().getContent();
        System.out.println(answer);
        JsonNode jsonNode = objectMapper.readTree(answer);
        String result = TaskHelper.postAnswer(jsonNode, token.getToken());
        System.out.println(result);
    }

    private static final String systemMessage = "dzisiejsza data to: 2023-11-14\n" +
            "\n" +
            "Chce żebyś sklasyfikował zadanie do wykonania do odpowiedniej kategorii:\n" +
            "\n" +
            "Kategoria 1: Calendar\n" +
            "Jeśli w zadaniu pojawi się jakaś wzmianka o dacie to zaklasyfikuj to zadanie do tej kategorii i zwróć obiek JSON w poniższym formacie.\n" +
            "np: Jutro mam spotkanie z Marianem\n" +
            "zwróć:\n" +
            "{\"tool\":\"Calendar\",\"desc\":\"Spotkanie z Marianem\",\"date\":\"2023-11-15\"}\n" +
            "\n" +
            "Kategoria 2: ToDo\n" +
            "Jeżeli w zdaniu pojawi się jakaś wzmianka o zadaniu do wykonania (bez daty) to zaklasyfikuj to zadanie do tej kategorii i zwróć obiekt JSON w poniższym formacie.\n" +
            "np: Przypomnij mi że mam kupić mleko\n" +
            "zwróć:\n" +
            "{\"tool\":\"ToDo\",\"desc\":\"Kup mleko\"}";
}
