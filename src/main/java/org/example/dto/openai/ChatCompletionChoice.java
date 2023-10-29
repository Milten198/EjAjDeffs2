package org.example.dto.openai;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatCompletionChoice {

    private int index;
    private ChatCompletionMessage message;
    private String finish_reason;
}
