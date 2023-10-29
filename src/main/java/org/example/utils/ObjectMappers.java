package org.example.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.day_1.Cookie;
import org.example.dto.common.Token;

public class ObjectMappers {

    static ObjectMapper objectMapper = new ObjectMapper();

    public static Token deserializeToken(String token) throws JsonProcessingException {
        return objectMapper.readValue(token, Token.class);
    }

    public static Cookie deserializeCookie(String cookie) throws JsonProcessingException {
        return objectMapper.readValue(cookie, Cookie.class);
    }
}
