package org.example.dto.day_14;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class SearchEmbedding {

    private UUID id;
    private List vector;
    private SearchTask payload;
}
