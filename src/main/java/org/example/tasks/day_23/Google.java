package org.example.tasks.day_23;

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
import org.springframework.web.client.RestTemplate;

@RestController
public class Google {


    @PostMapping("/solveGoogleApiTask")
    public ResponseEntity<String> solveGoogleApiTask() throws JsonProcessingException {
        Token token = TaskHelper.getToken("google");
        System.out.println("--Token: " + token.getToken());
        String result = TaskHelper.postAnswer("https://5d7e-2a02-a315-e542-5280-5c7-5839-d36d-d4b4.ngrok.io/serp", token.getToken());
        System.out.println("--Result: " + result);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/serp")
    public ResponseEntity<OwnApiResponse> ama(@Valid @RequestBody OwnApiRequest request) {
        System.out.println("--Question: " + request.getQuestion());
        ChatCompletionResponse chatCompletionResponse = OpenAIHelper.chatCompletion(request.getQuestion(),
                "Answer the question. If you are asked for the url return only the url. Shortly. If you don't know the answer say 'unknown'", "gpt-4-1106-preview");
        String evaluate = chatCompletionResponse.getChoices().get(0).getMessage().getContent();
        String answer;
        if (evaluate.equals("unknown")) {
            String serp = serpApi(request.getQuestion());
            ChatCompletionResponse serpResults = OpenAIHelper.chatCompletion(request.getQuestion(),
                    "As the answer to the question return only the url and nothing else" + serp, "gpt-4-1106-preview");
            answer = serpResults.getChoices().get(0).getMessage().getContent();
        } else {
            answer = evaluate;
        }

        OwnApiResponse response = new OwnApiResponse(answer);
        System.out.println("--Response: " + response.getReply());
        return ResponseEntity.ok(response);
    }


    private static String serpApi(String query) {
        RestTemplate restTemplate = new RestTemplate();
        String api_key = "ef269e6903d30ecf589e743cff5212833fe7b67b7b559dd6d294b7afeaaa0198";
        String url = String.format("https://serpapi.com/search?q=%s&api_key=%s", query, api_key);
        return restTemplate.getForObject(url, String.class);
    }
}
