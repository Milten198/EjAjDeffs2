package org.example.dto.openai.vision.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class VisionResponse {

    private String id;
    private String object;
    private long created;
    private String model;
    private VisionUsage usage;
    private List<VisionChoice> choices;
}
