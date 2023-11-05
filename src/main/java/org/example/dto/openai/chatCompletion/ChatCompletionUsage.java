package org.example.dto.openai.chatCompletion;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatCompletionUsage {

    private int prompt_tokens;
    private int completion_tokens;
    private int total_tokens;
}
