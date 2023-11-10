package org.example.dto.day_14.SearchQueryResponse;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ResponseForSearchQuery {

    private List<Result> result;
    private String status;
    private double time;
}
