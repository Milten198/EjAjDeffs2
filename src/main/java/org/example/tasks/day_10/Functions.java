package org.example.tasks.day_10;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.dto.common.Token;
import org.example.utils.TaskHelper;

public class Functions {

    public static void solveFunctionsApiTask() throws JsonProcessingException {
        Token token = TaskHelper.getToken("functions");
        System.out.println(token.getToken());
        String addUser = "{\n" +
                "    \"name\": \"addUser\",\n" +
                "    \"description\": \"Function to add a new user with given details.\",\n" +
                "    \"parameters\": {\n" +
                "        \"type\": \"object\",\n" +
                "        \"properties\": {\n" +
                "            \"name\": {\n" +
                "                \"type\": \"string\",\n" +
                "                \"description\": \"Name of the user.\"\n" +
                "            },\n" +
                "            \"surname\": {\n" +
                "                \"type\": \"string\",\n" +
                "                \"description\": \"Surname of the user.\"\n" +
                "            },\n" +
                "            \"year\": {\n" +
                "                \"type\": \"integer\",\n" +
                "                \"description\": \"Year of birth of the user.\"\n" +
                "            }\n" +
                "        }\n" +
                "    }\n" +
                "}\n";
        String answer = TaskHelper.postAnswer(addUser, token.getToken());
        System.out.println(answer);
    }
}
