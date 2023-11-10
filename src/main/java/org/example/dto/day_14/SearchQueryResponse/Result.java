package org.example.dto.day_14.SearchQueryResponse;

import lombok.Getter;
import lombok.Setter;
import org.example.dto.day_14.SearchTask;

@Getter
@Setter
public class Result {

    private String id;
    private int version;
    private double score;
    private SearchTask payload;
    private Object vector;
}
