package org.example.tasks.day_20;

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
public class OwnApiProController {

    private static String systemMessage = "Answer the question. Shortly. Sometimes you might need some extra context which should be below:";

    @PostMapping("/ama_pro")
    public ResponseEntity<OwnApiResponse> ama(@Valid @RequestBody OwnApiRequest request) {
        System.out.println("--Question: " + request.getQuestion());

        ChatCompletionResponse decider = OpenAIHelper
                .chatCompletion(request.getQuestion(),
                        "Decide if question or statement. If question return QUESTION, if not return STATEMENT.",
                        "gpt-4-1106-preview");
        String decide = decider.getChoices().get(0).getMessage().getContent();
        System.out.println("--Decide: " + decide);
        ChatCompletionResponse chatCompletionResponse;

        if (decide.equals("QUESTION")) {
            chatCompletionResponse = OpenAIHelper
                    .chatCompletion(request.getQuestion(), systemMessage, "gpt-4-1106-preview");
        } else {
            systemMessage += "\n" + request.getQuestion();
            chatCompletionResponse = OpenAIHelper
                    .chatCompletion(request.getQuestion(), "Shortly acknowledge, for example: 'Good to know!'", "gpt-4-1106-preview");
        }
        System.out.println("--System:\n" + systemMessage);

        String answer = chatCompletionResponse.getChoices().get(0).getMessage().getContent();
        OwnApiResponse response = new OwnApiResponse(answer);
        System.out.println("--Response: " + response.getReply());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/solveOwnApiProTask")
    public ResponseEntity<String> solveOwnApiTask() throws JsonProcessingException {
        Token token = TaskHelper.getToken("ownapipro");
        System.out.println("--Token: " + token.getToken());
        String result = TaskHelper.postAnswer("https://2d2b-46-149-211-8.ngrok.io/ama_pro", token.getToken());
        System.out.println("--Result: " + result);
        return ResponseEntity.ok(result);
    }
}
