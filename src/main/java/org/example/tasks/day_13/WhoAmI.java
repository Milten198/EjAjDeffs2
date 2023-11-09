package org.example.tasks.day_13;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.common.Token;
import org.example.dto.day_13.WhoAmITask;
import org.example.dto.openai.chatCompletion.ChatCompletionResponse;
import org.example.utils.OpenAIHelper;
import org.example.utils.TaskHelper;

import java.io.IOException;

public class WhoAmI {

    public static void solveWhoamiApiTask() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Token token = TaskHelper.getToken("whoami");

        String systemMessage = "Twoim zadaniem jest odgadnąć o jakiej postaci mowa. Jeśli wiesz, odpowiadasz TAK, jeśli nie wiesz odpowiadasz NIE";
        String userMessage = "";
        for (int i = 0; i < 10; i++) {
            String task = TaskHelper.getTask(token.getToken());
            WhoAmITask whoAmITask = objectMapper.readValue(task, WhoAmITask.class);
            System.out.printf("Hint: %s %s%n", i, whoAmITask.getHint());
            userMessage += whoAmITask.getHint() + "\n";
            ChatCompletionResponse chatCompletionResponse = OpenAIHelper.chatCompletion(userMessage, systemMessage, "gpt-4-1106-preview");
            String iKnowYou = chatCompletionResponse.getChoices().get(0).getMessage().getContent();
            System.out.println("Do you know yet? " + iKnowYou);
            if (iKnowYou.equals("TAK")) {
                break;
            }
        }
        String systemMessage2 = "Twoim zadaniem jest odgadnąć o jakiej postaci mowa. Podaj tylko imię i nazwisko.";
        ChatCompletionResponse chatCompletionResponse = OpenAIHelper.chatCompletion(userMessage, systemMessage2, "gpt-4-1106-preview");
        String answer = chatCompletionResponse.getChoices().get(0).getMessage().getContent();
        System.out.println(answer);
        String result = TaskHelper.postAnswer(answer, token.getToken());
        System.out.println(result);
    }
}
