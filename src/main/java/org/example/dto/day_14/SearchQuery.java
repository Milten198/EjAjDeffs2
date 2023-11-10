package org.example.dto.day_14;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SearchQuery {

    private Filter filter;
    private Integer limit;
    private List<Double> vector;
    private boolean with_payload;

}
