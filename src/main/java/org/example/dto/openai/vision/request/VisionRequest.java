package org.example.dto.openai.vision.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class VisionRequest {

    private String model;
    private List<Message> messages;
    private int maxTokens;
}
