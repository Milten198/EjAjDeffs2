package org.example.dto.openai;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ChatCompletionRequest {

    private String model;
    private List<ChatCompletionMessage> messages;

}
