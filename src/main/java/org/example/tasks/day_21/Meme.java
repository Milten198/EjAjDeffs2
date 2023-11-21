package org.example.tasks.day_21;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.common.Token;
import org.example.dto.day_21.MemeTask;
import org.example.dto.day_21.RenderImageResponse;
import org.example.utils.TaskHelper;

import java.io.IOException;

public class Meme {

    public static void solveMemeApiTask() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Token token = TaskHelper.getToken("meme");
        String task = TaskHelper.getTask(token.getToken());
        System.out.println(task);

        MemeTask memeTask = objectMapper.readValue(task, MemeTask.class);
        RenderImageResponse meme = MemeController.renderImage("odd-dragonflies-feed-justly-1664", memeTask.getText(), memeTask.getImage());

        String result = TaskHelper.postAnswer(meme.getHref(), token.getToken());
        System.out.println(result);
    }
}
