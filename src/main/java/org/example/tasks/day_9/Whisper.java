package org.example.tasks.day_9;

import org.example.dto.common.Token;
import org.example.dto.openai.whisper.WhisperResponse;
import org.example.utils.OpenAIHelper;
import org.example.utils.TaskHelper;

import java.io.IOException;

public class Whisper {

    public static void solveWhisperApiTask() throws IOException {
        Token token = TaskHelper.getToken("whisper");
        WhisperResponse whisper = OpenAIHelper.whisper("mateusz.mp3", "whisper-1");
        String result = TaskHelper.postAnswer(whisper.getText(), token.getToken());
        System.out.println(result);
    }
}
