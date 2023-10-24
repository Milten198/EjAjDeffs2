package org.example.tasks;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.dto.day_1.Cookie;
import org.example.dto.day_1.Token;
import org.example.utils.ObjectMappers;
import org.example.utils.RestTemplateHelper;

public class HelloApi {

    public static void solveHelloApiTask() throws JsonProcessingException {
        Token token = RestTemplateHelper.getToken("helloapi");
        String task = RestTemplateHelper.getTask(token.getToken());
        Cookie cookie = ObjectMappers.deserializeCookie(task);
        String answer = RestTemplateHelper.postAnswer(cookie.getCookie(), token.getToken());
        System.out.println("Answer:: " + answer);
    }
}
