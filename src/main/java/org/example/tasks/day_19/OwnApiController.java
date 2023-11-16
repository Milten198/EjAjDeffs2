package org.example.tasks.day_19;

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
public class OwnApiController {

    @PostMapping("/ama")
    public ResponseEntity<OwnApiResponse> ama(@Valid @RequestBody OwnApiRequest request) {
        System.out.println("--Question: " + request.getQuestion());
        ChatCompletionResponse chatCompletionResponse = OpenAIHelper.chatCompletion(request.getQuestion(), "Answer the question. Shortly.", "gpt-4-1106-preview");
        String answer = chatCompletionResponse.getChoices().get(0).getMessage().getContent();
        OwnApiResponse response = new OwnApiResponse(answer);
        System.out.println("--Response: " + response.getReply());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/solveOwnApiTask")
    public ResponseEntity<String> solveOwnApiTask() throws JsonProcessingException {
        Token token = TaskHelper.getToken("ownapi");
        System.out.println("--Token: " + token.getToken());
        String result = TaskHelper.postAnswer("https://6bf4-2a02-a315-e542-5280-61d6-51d6-8085-28.ngrok.io/ama", token.getToken());
        System.out.println("--Result: " + result);
        return ResponseEntity.ok(result);
    }
}
