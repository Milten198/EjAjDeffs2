package org.example.dto.openai.embedding;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class EmbeddingRequest {

    private String input;
    private String model;
}
