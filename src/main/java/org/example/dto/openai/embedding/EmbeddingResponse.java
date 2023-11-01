package org.example.dto.openai.embedding;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class EmbeddingResponse {

    private List<EmbeddingData> data;
    private String model;
    private String object;
    private EmbeddingUsage usage;
}
