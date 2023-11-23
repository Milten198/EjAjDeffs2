package org.example.tasks.day_24;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import org.example.dto.common.Token;
import org.example.dto.day_19.OwnApiRequest;
import org.example.dto.day_19.OwnApiResponse;
import org.example.dto.openai.chatCompletion.ChatCompletionResponse;
import org.example.utils.OpenAIHelper;
import org.example.utils.TaskHelper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Md2html {

    @PostMapping("/solveFineApiTask")
    public ResponseEntity<String> solveMd2htmlApiTask() throws JsonProcessingException {
        Token token = TaskHelper.getToken("md2html");
        System.out.println("--Token: " + token.getToken());
        String result = TaskHelper.postAnswer("https://51da-2a02-a315-e542-5280-d42-7b3a-677-c589.ngrok.io/fine", token.getToken());
        System.out.println("--Result: " + result);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/fine")
    public ResponseEntity<OwnApiResponse> fine(@Valid @RequestBody OwnApiRequest request) {
        System.out.println("--Question: " + request.getQuestion());
        ChatCompletionResponse chatCompletionResponse = OpenAIHelper.chatCompletion(request.getQuestion(), "Transform to HTML", "ft:gpt-3.5-turbo-1106:personal::8O7SslFB");
        String answer = chatCompletionResponse.getChoices().get(0).getMessage().getContent();
        OwnApiResponse response = new OwnApiResponse(answer);
        System.out.println("--Response: " + response.getReply());
        return ResponseEntity.ok(response);
    }
}
