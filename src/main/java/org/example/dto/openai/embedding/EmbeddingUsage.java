package org.example.dto.openai.embedding;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmbeddingUsage {

    private int prompt_tokens;
    private int total_tokens;
}
