package org.example.tasks.day_4;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.common.Token;
import org.example.dto.openai.ModerationResponse;
import org.example.dto.day_4.ModerationTask;
import org.example.utils.OpenAIHelper;
import org.example.utils.TaskHelper;

public class Moderation {

    public static void solveModerationApiTask() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Token token = TaskHelper.getToken("moderation");
        String task = TaskHelper.getTask(token.getToken());
        System.out.println("Task:: " + task);

        ModerationTask moderationTask = objectMapper.readValue(task, ModerationTask.class);

        int[] answer = new int[moderationTask.getInput().length];

        for (int i = 0; i < moderationTask.getInput().length; i++) {
            ModerationResponse moderate = OpenAIHelper.moderate(moderationTask.getInput()[i]);
            boolean flagged = moderate.getResults().get(0).isFlagged();
            if (flagged) {
                answer[i] = 1;
            } else {
                answer[i] = 0;
            }
        }
        String result = TaskHelper.postAnswer(answer, token.getToken());
        System.out.println("Result: " + result);
    }
}
