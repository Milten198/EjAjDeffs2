package org.example.dto.openai.moderation;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ModerationResponse {
    private String id;
    private String model;
    private List<ModerationResult> results;

}