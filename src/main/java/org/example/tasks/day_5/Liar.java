package org.example.tasks.day_5;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.common.Token;
import org.example.dto.day_5.LiarAnswer;
import org.example.dto.openai.ChatCompletionResponse;
import org.example.utils.OpenAIHelper;
import org.example.utils.TaskHelper;

public class Liar {

    public static void solveLiarApiTask() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Token token = TaskHelper.getToken("liar");
        String question = "What is the capital of Germany?";
        String answer = TaskHelper.postQuestion(question, token.getToken());
        LiarAnswer liarAnswer = objectMapper.readValue(answer, LiarAnswer.class);

        String userMessage = String.format("question: %s" +
                "liar respond: %s", question, liarAnswer.getAnswer());

        ChatCompletionResponse chatCompletionResponse = OpenAIHelper.chatCompletion(userMessage, "You are liar detector. User gives you question and the answer. " +
                "You need to verify if the answer to that answer is correct and respond only YES if correct and only NO if incorrect" +
                "Example_1:" +
                "question: What is the capital of Poland?" +
                "liar respond: The capital of Poland is Warsaw" +
                "your respond: YES" +
                "Example_2" +
                "question: What is the capital of Poland?" +
                "liar respond: It is very nice weather today" +
                "your respond: NO", "gpt-4");

        String verdict = chatCompletionResponse.getChoices().get(0).getMessage().getContent();
        System.out.println("Verdict:: " + verdict);

        String result = TaskHelper.postAnswer(verdict, token.getToken());
        System.out.println("Result: " + result);
    }
}
