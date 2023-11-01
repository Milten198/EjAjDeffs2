package org.example.dto.openai.embedding;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class EmbeddingData {

    private List<Double> embedding;
    private int index;
    private String object;
}
