package org.example.tasks.day_22;

import org.example.dto.common.Token;
import org.example.dto.openai.chatCompletion.ChatCompletionResponse;
import org.example.utils.OpenAIHelper;
import org.example.utils.TaskHelper;

import java.io.IOException;

public class OptimalDb {

    public static void solveOptimalDbApiTask() throws IOException {
        String textFromTheUrl = TaskHelper.getTextFromTheUrl("https://zadania.aidevs.pl/data/3friends.json");
        ChatCompletionResponse chatCompletionResponse = OpenAIHelper.chatCompletion(textFromTheUrl,
                "Skróć podany tekst jak najbardziej zachowując absolutnie wszystkie fakty o danej postaci, jak np taniec zygfryda na weselu, nie używaj żadnych dodatkowych znaków jak '-'. Chodzi o to by plik z tym tekstem zajmował jak najmniej miejsca.", "gpt-4-1106-preview");
        String summary = chatCompletionResponse.getChoices().get(0).getMessage().getContent();
        Token token = TaskHelper.getToken("optimaldb");
        String result = TaskHelper.postAnswer(summary, token.getToken());
        System.out.println(result);
    }
}
