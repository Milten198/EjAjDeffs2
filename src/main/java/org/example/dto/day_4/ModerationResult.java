package org.example.dto.day_4;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class ModerationResult {

    private boolean flagged;
    private Map<String, Boolean> categories;
    private Map<String, Double> category_scores;
}
