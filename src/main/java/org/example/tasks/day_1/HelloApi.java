package org.example.tasks.day_1;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.dto.day_1.Cookie;
import org.example.dto.common.Token;
import org.example.utils.ObjectMappers;
import org.example.utils.TaskHelper;

public class HelloApi {

    public static void solveHelloApiTask() throws JsonProcessingException {
        Token token = TaskHelper.getToken("helloapi");
        String task = TaskHelper.getTask(token.getToken());
        Cookie cookie = ObjectMappers.deserializeCookie(task);
        String answer = TaskHelper.postAnswer(cookie.getCookie(), token.getToken());
        System.out.println("Answer:: " + answer);
    }
}
