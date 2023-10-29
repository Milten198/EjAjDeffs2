package org.example.tasks.day_4;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.day_1.Token;
import org.example.dto.day_4.BloggerTask;
import org.example.dto.openai.ChatCompletionResponse;
import org.example.utils.OpenAIHelper;
import org.example.utils.TaskHelper;

public class Blogger {

    public static void solveBloggerApiTask() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Token token = TaskHelper.getToken("blogger");
        String task = TaskHelper.getTask(token.getToken());
        System.out.println("Task:: " + task);
        BloggerTask bloggerTask = objectMapper.readValue(task, BloggerTask.class);


        ChatCompletionResponse chatCompletionResponse_1 = OpenAIHelper.chatCompletion(bloggerTask.getBlog()[0],
                "Napisz krótki akapit na podany temat. Akapit rozpocznij od dokładnego powtórzenia podanego tematu odpowiednio sformatowanego.",
                "gpt-4");
        ChatCompletionResponse chatCompletionResponse_2 = OpenAIHelper.chatCompletion(bloggerTask.getBlog()[1],
                "Napisz krótki akapit na podany temat. Akapit rozpocznij od dokładnego powtórzenia podanego tematu odpowiednio sformatowanego.",
                "gpt-4");
        ChatCompletionResponse chatCompletionResponse_3 = OpenAIHelper.chatCompletion(bloggerTask.getBlog()[2],
                "Napisz krótki akapit na podany temat. Akapit rozpocznij od dokładnego powtórzenia podanego tematu odpowiednio sformatowanego.",
                "gpt-4");
        ChatCompletionResponse chatCompletionResponse_4 = OpenAIHelper.chatCompletion(bloggerTask.getBlog()[3],
                "Napisz krótki akapit na podany temat. Akapit rozpocznij od dokładnego powtórzenia podanego tematu odpowiednio sformatowanego.",
                "gpt-4");

        String blog_1 = chatCompletionResponse_1.getChoices().get(0).getMessage().getContent();
        String blog_2 = chatCompletionResponse_2.getChoices().get(0).getMessage().getContent();
        String blog_3 = chatCompletionResponse_3.getChoices().get(0).getMessage().getContent();
        String blog_4 = chatCompletionResponse_4.getChoices().get(0).getMessage().getContent();

        System.out.println("B1: " + blog_1);
        System.out.println("B2: " + blog_2);
        System.out.println("B3: " + blog_3);
        System.out.println("B4: " + blog_4);

        String[] answer = {blog_1, blog_2, blog_3, blog_4};
        String result = TaskHelper.postAnswer(answer, token.getToken());
        System.out.println("Result: " + result);
    }


}
