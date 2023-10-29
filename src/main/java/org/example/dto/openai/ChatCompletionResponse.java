package org.example.dto.openai;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ChatCompletionResponse {

    private String id;
    private String object;
    private long created;
    private String model;
    private List<ChatCompletionChoice> choices;
    private ChatCompletionUsage usage;

}
