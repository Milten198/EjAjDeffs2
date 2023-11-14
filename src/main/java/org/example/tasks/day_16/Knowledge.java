package org.example.tasks.day_16;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.common.Token;
import org.example.dto.day_16.Category;
import org.example.dto.day_16.Currency;
import org.example.dto.day_16.KnowledgeTask;
import org.example.dto.day_16.Population;
import org.example.dto.openai.chatCompletion.ChatCompletionResponse;
import org.example.utils.OpenAIHelper;
import org.example.utils.TaskHelper;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

public class Knowledge {


    public static void solveKnowledgeApiTask() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Token token = TaskHelper.getToken("knowledge");
        String task = TaskHelper.getTask(token.getToken());

        KnowledgeTask knowledgeTask = objectMapper.readValue(task, KnowledgeTask.class);
        String question = knowledgeTask.getQuestion();
        System.out.println("Question: " + question);

        ChatCompletionResponse chatCompletionResponse = OpenAIHelper.chatCompletion(question, systemMessage, "gpt-4");
        String decider = chatCompletionResponse.getChoices().get(0).getMessage().getContent();
        Category category = objectMapper.readValue(decider, Category.class);

        String result;

        if (category.getCategory().equals("country")) {
            Population populationOfCountry = Knowledge.getPopulationOfCountry(category.getValue());
            System.out.println("Population" + populationOfCountry.getPopulation());
            result = TaskHelper.postAnswer(populationOfCountry.getPopulation(), token.getToken());
        } else if (category.getCategory().equals("currency")) {
            Currency currency = Knowledge.getCurrency(category.getValue());
            System.out.println("Currency:" + currency);
            result = TaskHelper.postAnswer(currency.getRates()[0].getMid(), token.getToken());
        } else {
            ChatCompletionResponse response = OpenAIHelper.chatCompletion(question, "Udzielaj tylko konkretnej odpowiedz", "gpt-4");
            String answer = response.getChoices().get(0).getMessage().getContent();
            System.out.println(answer);
            result = TaskHelper.postAnswer(answer, token.getToken());
        }
        System.out.println(result);
    }

    public static Currency getCurrency(String currencyCode) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        String url = String.format("https://api.nbp.pl/api/exchangerates/rates/a/%s/?format=json", currencyCode);
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(restTemplate.getForObject(url, String.class), Currency.class);
    }

    public static Population getPopulationOfCountry(String country) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        String url = String.format("https://restcountries.com/v3.1/name/%s?fields=population", country);
        ObjectMapper objectMapper = new ObjectMapper();
        Population[] populations = objectMapper.readValue(restTemplate.getForObject(url, String.class), Population[].class);
        return populations[0];
    }

    private static final String systemMessage = "Twoim zadaniem jest zaklasyfikować podane przez użytkownika pytanie do jednej z trzech kategorii:\n" +
            "1. Jeżeli pytanie dotyczy populacji danego państwo zwróć JSON w formacie:\n" +
            "{\n" +
            "\"category\": \"conutry\",\n" +
            "\"value\": \"poland\"\n" +
            "}\n" +
            "Gdzie polska jest krajem o który pyta użytkownik\n" +
            "\n" +
            "2. Jeżeli pytanie dotyczy waluty zwróć kod waluty danego państwa, np:\n" +
            "podaj aktualny kurs EURO\n" +
            "{\n" +
            "\"category\": \"currency\",\n" +
            "\"value\": \"eur\"\n" +
            "}\n" +
            "\n" +
            "3. Jeżeli pytanie dotyczy czegokolwiek innego zwróć: \n" +
            "{\n" +
            "\"category\": \"other\",\n" +
            "\"value\": \"N/A\"\n" +
            "}";

}
