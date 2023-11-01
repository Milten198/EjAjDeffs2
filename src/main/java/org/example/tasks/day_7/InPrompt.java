package org.example.tasks.day_7;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.common.Token;
import org.example.dto.day_7.InPromptTask;
import org.example.dto.openai.ChatCompletionResponse;
import org.example.utils.OpenAIHelper;
import org.example.utils.TaskHelper;

public class InPrompt {

    public static void solveInPromptApiTask() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Token token = TaskHelper.getToken("inprompt");
        String task = TaskHelper.getTask(token.getToken());
        System.out.println(task);

        InPromptTask inPromptTask = objectMapper.readValue(task, InPromptTask.class);
        ChatCompletionResponse personDetails = OpenAIHelper.chatCompletion(inPromptTask.getQuestion(), "Return only the name from the user's question", "gpt-4");
        String personName = personDetails.getChoices().get(0).getMessage().getContent();

        String systemMessage = "";

        for (String input : inPromptTask.getInput()) {
            if (input.contains(personName)) {
                systemMessage = input;
                break;
            }
        }

        ChatCompletionResponse aboutPerson = OpenAIHelper.chatCompletion(inPromptTask.getQuestion(), systemMessage, "gpt-4");

        String result = TaskHelper.postAnswer(aboutPerson.getChoices().get(0).getMessage().getContent(), token.getToken());
        System.out.println("Result: " + result);
    }
}
