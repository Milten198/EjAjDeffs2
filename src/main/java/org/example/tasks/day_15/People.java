package org.example.tasks.day_15;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.common.Token;
import org.example.dto.day_15.PeopleTask;
import org.example.dto.day_15.Person;
import org.example.dto.openai.chatCompletion.ChatCompletionResponse;
import org.example.utils.OpenAIHelper;
import org.example.utils.TaskHelper;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class People {

    public static void solvePeopleApiTask() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        //get all data from page
        String textFromTheUrl = TaskHelper.getTextFromTheUrl("https://zadania.aidevs.pl/data/people.json");
        List<Person> people = Arrays.asList(objectMapper.readValue(textFromTheUrl, Person[].class));

        Token token = TaskHelper.getToken("people");
        String task = TaskHelper.getTask(token.getToken());
        PeopleTask peopleTask = objectMapper.readValue(task, PeopleTask.class);

        String getFullName = "zwróć JSON z imieniem i nazwiskiem z podanego pytania, np:\n" +
                "{\"imie\": \"Jan\", \"nazwisko\": \"Kowalski\"}\n" +
                "Używaj oficjalnych imion, czyli:\n" +
                "Tomek to Tomasz\n" +
                "Marysia to Maria\n" +
                "Ola to Aleksandra\n" +
                "itd\n";
        ChatCompletionResponse nameFullOfPerson = OpenAIHelper.chatCompletion(peopleTask.getQuestion(), getFullName, "gpt-4");

        Person personFullName = objectMapper.readValue(nameFullOfPerson.getChoices().get(0).getMessage().getContent(), Person.class);

        Person person = people.stream().filter(name -> name.getImie().equals(personFullName.getImie())
                && name.getNazwisko().equals(personFullName.getNazwisko())).collect(Collectors.toList()).get(0);

        String personDetails = String.format("Nazywam sie %s %s. %s Moim ulubionym kolorem jest %s.",
                person.getImie(), person.getNazwisko(), person.getO_mnie(), person.getUlubiony_kolor());

        ChatCompletionResponse responseToTheQuestion = OpenAIHelper.chatCompletion(peopleTask.getQuestion(), personDetails, "gpt-4-1106-preview");
        String response = responseToTheQuestion.getChoices().get(0).getMessage().getContent();

        String result = TaskHelper.postAnswer(response, token.getToken());
        System.out.println(result);

    }

}
