package org.example.tasks.day_12;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.common.Token;
import org.example.dto.day_12.ScraperTask;
import org.example.dto.openai.chatCompletion.ChatCompletionResponse;
import org.example.utils.OpenAIHelper;
import org.example.utils.TaskHelper;

import java.io.IOException;

import static org.example.utils.TaskHelper.getTextFromTheUrl;

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
}
