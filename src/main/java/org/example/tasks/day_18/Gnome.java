package org.example.tasks.day_18;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.common.Token;
import org.example.dto.day_18.GnomeTask;
import org.example.dto.openai.vision.response.VisionResponse;
import org.example.utils.OpenAIHelper;
import org.example.utils.TaskHelper;

import java.io.IOException;

public class Gnome {

    public static void solveGnomeApiTask() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Token token = TaskHelper.getToken("gnome");
        String task = TaskHelper.getTask(token.getToken());
        GnomeTask gnomeTask = objectMapper.readValue(task, GnomeTask.class);
        System.out.println(task);

        VisionResponse vision = OpenAIHelper.vision("Jaki jest kolor czarpki gnoma? Jeśli nie możesz ustalić zwróć ERROR jako odpowiedź", gnomeTask.getUrl());
        String answer = vision.getChoices().get(0).getMessage().getContent();
        System.out.println(answer);
        String result = TaskHelper.postAnswer(answer, token.getToken());
        System.out.println(result);
    }
}
